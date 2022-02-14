namespace AuthMe.Domain.Common;

public class Event<T>
{
    public string Type { get; set; }
    public T Model { get; set; }
}

