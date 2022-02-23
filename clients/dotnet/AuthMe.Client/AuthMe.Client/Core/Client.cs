using System.Net.Http.Json;
using System.Security.AccessControl;
using System.Text.RegularExpressions;
using AuthMe.Client.Models;

namespace AuthMe.Client.Core;

public class Client
{
    private const string Endpoint = "http://localhost:8081";
    private readonly HttpClient _client;

    private string _platinumLeft = string.Empty;
    private string _goldenToken = string.Empty;
    //private string _cookie = string.Empty;
    
    public Client()
    {
        _client = new HttpClient { BaseAddress = new Uri(Endpoint) };
    }

    public async Task<bool> TriggerValidationProcess(string goldenToken, string issuerName)
    {
        _goldenToken = goldenToken;
        var request = Requests.CreateTriggerProcessRequest(goldenToken, issuerName);
        var response = await _client.SendAsync(request);
        
        var model = await response.Content.ReadFromJsonAsync<TriggerValidationResponse>();

        if (model!.Status == "triggered")
        {
            _platinumLeft = model.PlatinumToken;
            return true;
        }
        
        return false;
    }

    public async Task<bool> Validate(string platinumToken, Identity identity)
    {
        var request = Requests.CreateValidationRequest(_goldenToken, _platinumLeft, platinumToken, identity);
        var response = await _client.SendAsync(request);
        
        var model = await response.Content.ReadFromJsonAsync<ValidationResponse>();
        return model!.Result == "data-valid";
    }

    private string ExtractCookie(HttpResponseMessage response)
    {
        var cookies = response.Headers.SingleOrDefault(header => header.Key == "Set-Cookie").Value;
        foreach (var cookie in cookies)
        {
            var regex = new Regex(@"(?<=JSESSIONID=)(.*)(?=; Path=/; HttpOnly;)");
            var match = regex.Match(cookie);
            if (match.Success)
                return match.Groups[1].ToString();
        }

        return "";
    }
}