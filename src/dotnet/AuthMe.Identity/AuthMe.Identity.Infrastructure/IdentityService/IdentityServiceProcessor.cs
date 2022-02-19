using AuthMe.Domain.Common;
using AuthMe.Domain.Events;
using AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;
using AuthMe.IdentityMsrv.Infrastructure.Settings;
using Azure.Messaging.ServiceBus;
using MediatR;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;
using EventHandler = AuthMe.Domain.Common.EventHandler;

namespace AuthMe.IdentityMsrv.Infrastructure;

public class IdentityServiceProcessor : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityServiceProcessor(IOptions<IdentityServiceProcessorSettings> options, IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
        _client = new ServiceBusClient(options.Value.Endpoint);
        _processor = _client.CreateProcessor(options.Value.Queue);

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;

        _processor.StartProcessingAsync().Wait();
    }

    private async Task MessageHandler(ProcessMessageEventArgs args)
    {
        var handler = new EventHandler(args.Message.Body.ToString());
        
        await handler.On<ValidateIdentityCompletedEvent>(async @event =>
        {
            using var scope = _serviceProvider.CreateScope();
            var mediatr = scope.ServiceProvider.GetService<IMediator>();

            var model = @event.Model;
            var command = new UpdateIdentityCommand
            {
                Id = model.Id,
                Name = model.Name,
                MiddleName = model.MiddleName,
                Surname = model.Surname,
                DateOfBirth = model.DateOfBirth
            };

            var response = await mediatr!.Send(command);
            
            return true;
        });

        if (handler.Handled)
            await args.CompleteMessageAsync(args.Message);
        else
        {
            await args.AbandonMessageAsync(args.Message);
            await Task.Delay(250);
        }
    }
    
    private async Task ErrorHandler(ProcessErrorEventArgs args)
    {
        Console.WriteLine(args.Exception.ToString());
        await Task.CompletedTask;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        
    }
}