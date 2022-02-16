using AuthMe.Domain.Common;
using AuthMe.Domain.Events;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;
using Azure.Messaging.ServiceBus;
using MediatR;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Hosting;
using EventHandler = AuthMe.Domain.Common.EventHandler;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;

public class IdentityDocumentProcessor : BackgroundService
{
    private readonly IMediator _mediator;
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityDocumentProcessor(IConfiguration configuration, IMediator mediator)
    {
        _mediator = mediator;
        _client = new ServiceBusClient(configuration["AzureServiceBusEndpoint"]);
        _processor = _client.CreateProcessor("identity_validity");

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;

        _processor.StartProcessingAsync().Wait();
    }

    private async Task MessageHandler(ProcessMessageEventArgs args)
    {
        var handler = new EventHandler(args.Message.Body.ToString());
        
        handler.On<ValidateIdentityEvent>(@event =>
        {
            var query = new ReadIdentityDocumentQuery
            {
                DocumentId = @event.Model
            };

            var task = _mediator.Send(query);
            task.Wait();
            
            return true;
        });
        
        await args.CompleteMessageAsync(args.Message);
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