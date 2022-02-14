using AuthMe.Application.Common.Async;
using Azure.Messaging.ServiceBus;

namespace AuthMe.Infrastructure.Async;

public abstract class AzureBusReceiver<T> : IBusReceiver<T> where T : class
{
    protected readonly ServiceBusClient Client;
    protected readonly ServiceBusSender Sender;

    protected AzureBusReceiver(string connectionString, string queueName)
    {
        Client = new ServiceBusClient(connectionString);
        Sender = Client.CreateSender(queueName);
    }

    public async Task<T> Receive()
    {
        throw new NotImplementedException();
    }
}