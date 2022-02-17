using System.Text.Json;
using AuthMe.Domain.Common;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Configuration;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.ServiceBus;

public class AzureServiceBus : IServiceBus
{
    private readonly ServiceBusClient _client;
    private readonly Dictionary<string, ServiceBusSender> _senders;

    public AzureServiceBus(IConfiguration configuration)
    {
        _client = new ServiceBusClient(configuration["AzureServiceBusEndpoint"]);
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