namespace AuthMe.Client.Core;

[AttributeUsage(AttributeTargets.Method, AllowMultiple = false, Inherited = true)]
internal class RouteAttribute : Attribute
{
    public string Endpoint { get; init; }

    public RouteAttribute(string endpoint)
    {
        Endpoint = endpoint;
    }
}