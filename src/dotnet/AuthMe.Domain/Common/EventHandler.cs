using System.Reflection;
using System.Text.Json;
using System.Text.Json.Serialization;
using Newtonsoft.Json;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace AuthMe.Domain.Common;

public class EventHandler
{
    private readonly string _json;
    private readonly Event? _event;

    public EventHandler(string json)
    {
        _json = json;
        _event = JsonSerializer.Deserialize<Event>(json);
    }
    
    public void On<T>(Func<T, bool> processor)
    {
        var type = typeof(T);
        if (_event!.Type == type.Name)
        {
            var e = JsonSerializer.Deserialize<T>(_json, new JsonSerializerOptions() {WriteIndented = true});
            processor.Invoke(e);
        }
    }
}