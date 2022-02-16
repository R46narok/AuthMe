using System.Net.Http.Headers;
using System.Text;
using System.Text.RegularExpressions;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Http;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Azure.Messaging.ServiceBus;
using Microsoft.Extensions.Hosting;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService;

public class IdentityDocumentValidityService : IIdentityDocumentValidityService
{
    private readonly IHttpClientFactory _httpClientFactory;

    public IdentityDocumentValidityService(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }
    
    public async Task<bool> IsValidAsync(string documentNumber, DateTime dateOfBirth)
    {
        var client = _httpClientFactory.CreateClient("MinistryOfInterior");

        var response = await client.SendAsync(MoIRequest.TriggerCsrf());
        var cookies = response.Headers.SingleOrDefault(header => header.Key == "Set-Cookie").Value;

        var validationCookie = MoiRequestCookies.ExtractRequestVerificationToken(cookies);
        var aspNetCookie = MoiRequestCookies.ExtractAspNetSession(cookies);
        var validation = response.ExtractRequestVerificationToken();

        var request = new HttpRequestMessage(new HttpMethod("POST"), "");
        request.Headers.TryAddWithoutValidation("authority", "www.mvr.bg");
        request.Headers.TryAddWithoutValidation("cache-control", "max-age=0");
        request.Headers.TryAddWithoutValidation("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\"");
        request.Headers.TryAddWithoutValidation("sec-ch-ua-mobile", "?0");
        request.Headers.TryAddWithoutValidation("sec-ch-ua-platform", "\"Windows\"");
        request.Headers.TryAddWithoutValidation("upgrade-insecure-requests", "1");
        request.Headers.TryAddWithoutValidation("origin", "https://www.mvr.bg");
        request.Headers.TryAddWithoutValidation("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36");
        request.Headers.TryAddWithoutValidation("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        request.Headers.TryAddWithoutValidation("sec-fetch-site", "same-origin");
        request.Headers.TryAddWithoutValidation("sec-fetch-mode", "navigate");
        request.Headers.TryAddWithoutValidation("sec-fetch-user", "?1");
        request.Headers.TryAddWithoutValidation("sec-fetch-dest", "document");
        request.Headers.TryAddWithoutValidation("referer", "https://www.mvr.bg/en/administrative-services/e-services/validity-verification-of-bulgarian-personal-documents");
        request.Headers.TryAddWithoutValidation("accept-language", "en-US,en;q=0.9");
        request.Headers.TryAddWithoutValidation("cookie", $"_ga=GA1.2.1377375796.1644746230; _gid=GA1.2.2064337862.1644746230; ASP.NET_SessionId={aspNetCookie.Value}; __RequestVerificationToken={validationCookie.Value}"); 

        var contentList = new List<string>();
        contentList.Add($"ctl05_TSM={Uri.EscapeDataString(";;System.Web.Extensions,+Version=4.0.0.0,+Culture=neutral,+PublicKeyToken=31bf3856ad364e35:en-US:4ff39ab4-86bc-4f97-a397-bc04a8fc5f51:ea597d4b:b25378d2;;Telerik.Sitefinity.Resources:en-US:60e7e793-3cce-4836-9221-605a2adedd73:b162b7a1:cda154af")}");
        contentList.Add($"ctl06_TSSM={Uri.EscapeDataString(";Telerik.Sitefinity.Resources,+Version=13.1.7424.0,+Culture=neutral,+PublicKeyToken=b28c218413bdf563:en:60e7e793-3cce-4836-9221-605a2adedd73:7a90d6a")}");
        contentList.Add($"__EVENTTARGET={Uri.EscapeDataString("")}");
        contentList.Add($"__EVENTARGUMENT={Uri.EscapeDataString("")}");
        contentList.Add($"__VIEWSTATE={Uri.EscapeDataString("/wEPDwUKLTI1NDA1MjMyMmRkm7zjo5BP43BviPva1WmxPf927vQBjvFSMrLDdJlsxq8=")}");
        contentList.Add($"__VIEWSTATEGENERATOR={Uri.EscapeDataString("9AB30B57")}");
        contentList.Add($"ctl00$ctl05={Uri.EscapeDataString("")}");
        contentList.Add($"__RequestVerificationToken={Uri.EscapeDataString($"{validation.Value}")}");
        contentList.Add($"DocumentKindCode={Uri.EscapeDataString("6729")}");
        contentList.Add($"DocumentNumber={Uri.EscapeDataString("123456789")}");
        contentList.Add($"BirthsDayTXT={Uri.EscapeDataString("05.08.2005")}");
        request.Content = new StringContent(string.Join("&", contentList));
        request.Content.Headers.ContentType = MediaTypeHeaderValue.Parse("application/x-www-form-urlencoded");

        response = await client.SendAsync(request);
        
        var stream = await response.Content.ReadAsStreamAsync();
        var bytes = new byte[stream.Length];
        await stream.ReadAsync(bytes, 0, (int) stream.Length);
        var str = Encoding.UTF8.GetString(bytes);
        await File.WriteAllTextAsync("index.html", str);
        
        return false;
    }
}