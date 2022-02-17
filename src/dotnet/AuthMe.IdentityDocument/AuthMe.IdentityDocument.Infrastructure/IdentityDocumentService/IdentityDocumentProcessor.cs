using AuthMe.Domain.Common;
using AuthMe.Domain.Events;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;
using Azure.Messaging.ServiceBus;
using MediatR;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using EventHandler = AuthMe.Domain.Common.EventHandler;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;

public class IdentityDocumentProcessor : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityDocumentProcessor(IConfiguration configuration, IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
        _client = new ServiceBusClient(configuration["AzureServiceBusEndpoint"]);
        _processor = _client.CreateProcessor("identity_validity");

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;

        _processor.StartProcessingAsync().Wait();
    }

    private async Task MessageHandler(ProcessMessageEventArgs args)
    {
        var handler = new EventHandler(args.Message.Body.ToString());
        
        await handler.On<ValidateIdentityEvent>(async @event =>
        {
            using var scope = _serviceProvider.CreateScope();
            var mediatr = scope.ServiceProvider.GetService<IMediator>();
            var bus = scope.ServiceProvider.GetService<IServiceBus>();
            
            var query = new ReadIdentityDocumentQuery {IdentityId = @event.Model};
            var response = await mediatr!.Send(query);

            await bus.Send(new ValidateIdentityCompletedEvent(new ValidateIdentityCompletedModel
            {
                Id = @event.Model,
                Name = response.Result.Name,
                MiddleName = response.Result.MiddleName,
                Surname = response.Result.Surname,
                DateOfBirth = response.Result.DateOfBirth
            }),"identity_validity");
            
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