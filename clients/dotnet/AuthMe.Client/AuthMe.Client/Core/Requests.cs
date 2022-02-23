using System.Net;
using System.Net.Http.Headers;
using AuthMe.Client.Models;

namespace AuthMe.Client.Core;

internal static class Requests
{
    [Route("/api/identity/check")]
    public static HttpRequestMessage CreateTriggerProcessRequest(string goldenToken, string issuerName)
    {
        var request = new HttpRequestMessage(HttpMethod.Post, GetRouteForMethod("CreateTriggerProcessRequest"));
        
        request.Headers.Add("goldenToken", goldenToken);
        request.Headers.Add("issuerName", issuerName);

        return request;
    }

    [Route("/api/identity/check/validate")]
    public static HttpRequestMessage CreateValidationRequest(
        string goldenToken,
        string platinumLeft, string platinumRight,
        Identity identity)
    {
        var request = new HttpRequestMessage(HttpMethod.Post, GetRouteForMethod("CreateValidationRequest"));
        
        request.Headers.TryAddWithoutValidation("goldenToken", goldenToken);
        request.Headers.TryAddWithoutValidation("platinumTokenLeft", platinumLeft);
        request.Headers.TryAddWithoutValidation("platinumTokenRight", platinumRight);

        var multipartContent = new MultipartFormDataContent();
        multipartContent.Add(new StringContent(identity.Name), "name");
        multipartContent.Add(new StringContent(identity.MiddleName), "middleName");
        multipartContent.Add(new StringContent(identity.Surname), "surname");
        multipartContent.Add(new StringContent(identity.DateOfBirth), "dateOfBirth");
        request.Content = multipartContent; 

        return request;
    }
    
    private static string GetRouteForMethod(string name)
    {
        var method = typeof(Requests).GetMethod(name);
        var attr = (RouteAttribute)method.GetCustomAttributes(typeof(RouteAttribute), true)[0] ;
        return attr.Endpoint;
    }
}