using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Hosting;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;

public class IdentityDocumentProcessor : BackgroundService
{
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityDocumentProcessor(string connectionString, string queueName)
    {
        _client = new ServiceBusClient(connectionString);
        _processor = _client.CreateProcessor(queueName);

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;

        _processor.StartProcessingAsync().Wait();
    }

    private async Task MessageHandler(ProcessMessageEventArgs args)
    {
        Console.WriteLine(args.Message.Body.ToString());
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