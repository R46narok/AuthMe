namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Http;

public static class MoIRequest
{
    public static HttpRequestMessage TriggerCsrf()
    {
        var builder = new MoIRequestBuilder(HttpMethod.Get);
        return builder.BuildRequest();
    }

    public static HttpRequestMessage ValidateDocument(
        string validationCookieToken, string aspNetCookie, string validationToken, 
        string documentNumber, string dateOfBirth)
    {
        var builder = new MoIRequestBuilder(HttpMethod.Post);
        
        builder.AddBrowserSimulation()
            .AddCookies(validationCookieToken, aspNetCookie)
            .AddContent()
            .AddTelerikUi()
            .AddRequestVerificationToken(validationToken)
            .AddDocument(documentNumber, dateOfBirth);

        return builder.BuildRequest();
    }
}