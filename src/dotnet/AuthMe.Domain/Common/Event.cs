namespace AuthMe.Domain.Common;

public class Event
{
    public string Type { get; set; }
}


public class Event<T> : Event
{
    public T Model { get; set; }
}

