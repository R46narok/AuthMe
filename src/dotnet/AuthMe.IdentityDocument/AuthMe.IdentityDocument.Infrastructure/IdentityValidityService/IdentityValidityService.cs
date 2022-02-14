using System.Net.NetworkInformation;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Hosting;

namespace AuthMe.Infrastructure.IdentityValidityService;

public class IdentityValidityService : BackgroundService, IIdentityValidityService
{
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;
    
    public IdentityValidityService(string connectionString, string queueName)
    {
        _client = new ServiceBusClient(connectionString);
        _processor = _client.CreateProcessor(queueName);

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;

        _processor.StartProcessingAsync().Wait();
    }
    
    /// <summary>
    /// Begin the validation process 
    /// </summary>
    /// <param name="documentNumber"></param>
    /// <param name="dateOfBirth"></param>
    /// <returns></returns>
    /// <exception cref="NotImplementedException"></exception>
    public bool IsValid(string documentNumber, DateTime dateOfBirth)
    {
        throw new NotImplementedException();
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