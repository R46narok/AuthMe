using System.Diagnostics;
using AuthMe.Domain.Common;
using AuthMe.Domain.Events;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Options;
using EventHandler = AuthMe.Domain.Common.EventHandler;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrValidityService;

public class OcrValidityService : IOcrValidityService
{
    private readonly ServiceBusClient _client;
    private readonly ServiceBusProcessor _processor;

    private long _nextId = -1;
    private bool _processedEvent = false;
    
    public OcrValidityService(IOptions<AzureServiceBusSettings> options)
    {
        _client = new ServiceBusClient(options.Value.Endpoint);
        _processor = _client.CreateProcessor(options.Value.ValidityOcrQueue);

        _processor.ProcessMessageAsync += MessageHandler;
        _processor.ProcessErrorAsync += ErrorHandler;
    }

    private async Task MessageHandler(ProcessMessageEventArgs args)
    {
        var handler = new EventHandler(args.Message.Body.ToString());
        await handler.On<ValidateOcrEvent>(async @event =>
        {
            _nextId = @event.Model;
            return true;
        });

        if (handler.Handled)
            await args.CompleteMessageAsync(args.Message);
        else
            await args.AbandonMessageAsync(args.Message);
        
        _processedEvent = true;
    }
    
    private async Task ErrorHandler(ProcessErrorEventArgs args)
    {
        Console.WriteLine(args.Exception.ToString());
        await Task.CompletedTask;
    }

    public async Task<long> NextIdentityId()
    {
        await _processor.StartProcessingAsync();

        var stopwatch = new Stopwatch();
        stopwatch.Start();
        while (!_processedEvent)
        {
            if (stopwatch.ElapsedMilliseconds == 5000)
            {
                _processedEvent = false;
                await _processor.StopProcessingAsync();
                return -1;
            }
        }
        await _processor.StopProcessingAsync();

        _processedEvent = false;
        return _nextId;
    }
}