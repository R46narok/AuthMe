namespace AuthMe.Application.Common.Async;

public interface IBusSender<T> where T : class
{
    Task<bool> SendAsync(T model);
}