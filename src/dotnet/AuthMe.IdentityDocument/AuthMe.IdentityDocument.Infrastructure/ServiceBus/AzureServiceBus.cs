using System.Text.Json;
using AuthMe.Domain.Common;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.ServiceBus;

public class AzureServiceBus : IServiceBus
{
    private readonly ServiceBusClient _client;
    private readonly ServiceBusSender _validitySender;
    private readonly ServiceBusSender _ocrSender;

    public AzureServiceBus(IOptions<AzureServiceBusSettings> options)
    {
        _client = new ServiceBusClient(options.Value.Endpoint);
        _validitySender = _client.CreateSender(options.Value.ValidityQueue);
        _ocrSender = _client.CreateSender(options.Value.ValidityOcrQueue);
    }

    public async Task Send<T>(Event<T> e, string queue)
    {
        var json = JsonSerializer.Serialize(e);
        var message = new ServiceBusMessage(json);

        switch (queue)
        {
            case "identity_validity":
                await _validitySender.SendMessageAsync(message);
                break;
            case "identity_ocr_validity":
                await _ocrSender.SendMessageAsync(message);
                break;
        }
    }
}