using System.Net;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Http;

public class MoIRequestContentBuilder
{
    private List<KeyValuePair<string, string>> _valuePairs;
    
    public MoIRequestContentBuilder()
    {
        _valuePairs = new List<KeyValuePair<string, string>>();
    }

    public MoIRequestContentBuilder AddTelerikUi()
    {
        _valuePairs.Add(new ("ctl05_TSM", ";;System.Web.Extensions,+Version=4.0.0.0,+Culture=neutral,+PublicKeyToken=31bf3856ad364e35:en-US:4ff39ab4-86bc-4f97-a397-bc04a8fc5f51:ea597d4b:b25378d2;;Telerik.Sitefinity.Resources:en-US:60e7e793-3cce-4836-9221-605a2adedd73:b162b7a1:cda154af"));
        _valuePairs.Add(new ("ctl06_TSSM", ";Telerik.Sitefinity.Resources,+Version=13.1.7424.0,+Culture=neutral,+PublicKeyToken=b28c218413bdf563:en:60e7e793-3cce-4836-9221-605a2adedd73:7a90d6a"));
        _valuePairs.Add(new ("ctl00$ctl05", ""));
        
        _valuePairs.Add(new ("__EVENTTARGET", ""));
        _valuePairs.Add(new ("__VIEWSTATE", "/wEPDwUKLTI1NDA1MjMyMmRkm7zjo5BP43BviPva1WmxPf927vQBjvFSMrLDdJlsxq8="));
        _valuePairs.Add(new ("__VIEWSTATEGENERATOR", "9AB30B57"));
        
        return this;
    }

    public MoIRequestContentBuilder AddDocument(string documentNumber, string dateOfBirth)
    {
        _valuePairs.Add(new ("DocumentKindCode", "6729"));
        _valuePairs.Add(new ("DocumentNumber", documentNumber));
        _valuePairs.Add(new ("BirthsDayTXT", dateOfBirth));
        
        return this;
    }

    public MoIRequestContentBuilder AddRequestVerificationToken(string token)
    {
        _valuePairs.Add(new ("__RequestVerificationToken", token));

        return this;
    }
    
    public FormUrlEncodedContent BuildContent()
    {
        return new FormUrlEncodedContent(_valuePairs);
    }
}

public class MoIRequestBuilder
{
    private HttpRequestMessage _request;
    private MoIRequestContentBuilder _contentBuilder;
    
    public MoIRequestBuilder(HttpMethod method)
    {
        _request = new HttpRequestMessage(method, "");
        _contentBuilder = new MoIRequestContentBuilder();
    }

    public MoIRequestBuilder AddBrowserSimulation()
    {
        _request.Headers.Add("cache-control", "max-age-0");
        _request.Headers.Add("sec-ch-ua-platform", "Windows");
        _request.Headers.Add("upgrade-insecure-requests", "1");
        _request.Headers.Add("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        _request.Headers.Add("origin", "https://www.mvr.bg");
        _request.Headers.Add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36");
        _request.Headers.Add("sec-fetch-site", "same-origin");
        _request.Headers.Add("sec-fetch-mode", "navigate");
        _request.Headers.Add("sec-fetch-user", "?1");
        _request.Headers.Add("sec-fetch-dest", "document");
        _request.Headers.Add("referer", "https://www.mvr.bg/en/administrative-services/e-services/validity-verification-of-bulgarian-personal-documents");
        _request.Headers.Add("accept-language", "en-US,en;q=0.9");

        return this;
    }

    public MoIRequestBuilder AddCookies(string requestValidationToken, string aspNetSessionId)
    {
        _request.Headers.Add("cookie", $"__RequestVerificationToken={requestValidationToken}; ASP.NET_SessionId={aspNetSessionId}");
        
        return this;
    }
    
    public MoIRequestContentBuilder AddContent()
    {
        return _contentBuilder;
    }
    
    public void Reset()
    {
        _request = new HttpRequestMessage();
    }

    public HttpRequestMessage BuildRequest()
    {
        _request.Content = _contentBuilder.BuildContent();
        return _request;
    }
}