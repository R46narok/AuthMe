namespace AuthMe.Infrastructure.IdentityValidityService;

public class MoIRequestBuilder
{
    public static HttpRequestMessage EstablishSessionRequest => new HttpRequestMessage(HttpMethod.Get, "")
    {
        Content = null
    };

    public static HttpRequestMessage ValidityCheckRequest => new HttpRequestMessage(HttpMethod.Post, "")
    {

    };
}