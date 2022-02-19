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
    private readonly Dictionary<string, ServiceBusSender> _senders;

    public AzureServiceBus(IOptions<AzureServiceBusSettings> options)
    {
        _client = new ServiceBusClient(options.Value.Endpoint);
        _senders = new Dictionary<string, ServiceBusSender>();
    }

    public async Task Send<T>(Event<T> e, string queue)
    {
        if (!_senders.ContainsKey(queue))
            _senders.Add(queue, _client.CreateSender(queue));
        
        var json = JsonSerializer.Serialize(e);
        await _senders[queue].SendMessageAsync(new ServiceBusMessage(json));
    }
}