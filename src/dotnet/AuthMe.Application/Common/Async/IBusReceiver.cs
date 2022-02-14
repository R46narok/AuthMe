namespace AuthMe.Application.Common.Async;

public interface IBusReceiver<T> where T : class
{
    public Task<T> Receive();
}