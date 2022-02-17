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

    public bool Handled { get; private set; } = false;
    
    public EventHandler(string json)
    {
        _json = json;
        _event = JsonSerializer.Deserialize<Event>(json);
    }
    
    public async Task On<T>(Func<T, Task<bool>> processor)
    {
        var type = typeof(T);
        if (_event!.Type == type.Name)
        {
            var e = JsonSerializer.Deserialize<T>(_json, new JsonSerializerOptions() {WriteIndented = true});
            Handled = await processor.Invoke(e);
        }
    }
}