using AuthMe.Domain.Events;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.UpdateIdentityDocument;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;
using Azure.Messaging.ServiceBus;
using MediatR;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;
using EventHandler = AuthMe.Domain.Common.EventHandler;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;

public class IdentityDocumentProcessor : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityDocumentProcessor(IOptions<AzureServiceBusSettings> options, IServiceProvider serviceProvider)
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
        
        await handler.On<ValidateIdentityEvent>(async @event =>
        {
            using var scope = _serviceProvider.CreateScope();
            var mediatr = scope.ServiceProvider.GetService<IMediator>();
            var bus = scope.ServiceProvider.GetService<IServiceBus>();
            
            var query = new ReadIdentityDocumentQuery {IdentityId = @event.Model};
            var response = await mediatr!.Send(query);

            var command = new UpdateIdentityDocumentCommand
            {
                IdentityId = @event.Model,
                OcrName = response.Result.Name,
                OcrMiddleName = response.Result.MiddleName,
                OcrSurname = response.Result.Surname,
                OcrDateOfBirth = response.Result.DateOfBirth?.ToString("dd-MM-yyyy")
            };
            await mediatr.Send(command);
            
            await bus!.Send(new ValidateIdentityCompletedEvent(new ValidateIdentityCompletedModel
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

    protected override Task ExecuteAsync(CancellationToken stoppingToken)
    {
        return Task.CompletedTask;
    }
}