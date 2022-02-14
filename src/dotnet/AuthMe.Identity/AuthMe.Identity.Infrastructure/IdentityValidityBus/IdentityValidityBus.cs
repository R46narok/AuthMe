using System.Text.Json;
using AuthMe.Domain.Common;
using AuthMe.IdentityService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;

namespace AuthMe.IdentityService.Infrastructure.IdentityValidityBus;

public class IdentityValidityBus : IIdentityValidityBus
{
    private readonly ServiceBusClient _client;
    private readonly ServiceBusSender _sender;

    public IdentityValidityBus(string connectionString, string queueName)
    {
        _client = new ServiceBusClient(connectionString);
        _sender = _client.CreateSender(queueName);
    }

    public async Task Send<T>(Event<T> e)
    {
        var json = JsonSerializer.Serialize(e);
        await _sender.SendMessageAsync(new ServiceBusMessage(json));
    }
}