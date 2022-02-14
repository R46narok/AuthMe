using System.Reflection;
using System.Text.Json;
using AuthMe.Application.Common.Async;
using Azure.Messaging.ServiceBus;

namespace AuthMe.Infrastructure.Async;

public abstract class AzureBusSender<T> : IBusSender<T> where T : class
{
    protected readonly ServiceBusClient Client;
    protected readonly ServiceBusSender Sender;

    protected AzureBusSender(string connectionString, string queueName)
    {
        Client = new ServiceBusClient(connectionString);
        Sender = Client.CreateSender(queueName);
    }
    
    public virtual async Task<bool> SendAsync(T model)
    {
        using var batch = await Sender.CreateMessageBatchAsync();

        var json = JsonSerializer.Serialize(model);
        if (!batch.TryAddMessage(new ServiceBusMessage(json)))
            return false;

        try
        {
            await Sender.SendMessagesAsync(batch);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}