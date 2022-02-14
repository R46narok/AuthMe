using System.Net;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Infrastructure.OcrService.Models;
using Microsoft.Extensions.Logging;

namespace AuthMe.Infrastructure.OcrService;

public class OcrService : IOcrService
{
    private readonly ILogger<OcrService> _logger;
    private readonly IHttpClientFactory _httpFactory;

    private const int TimeoutMs = 5000;
    
    public OcrService(ILogger<OcrService> logger, IHttpClientFactory httpFactory)
    {
        _logger = logger;
        _httpFactory = httpFactory;
    }
    
    /// <summary>
    /// 
    /// </summary>
    /// <param name="image"></param>
    /// <returns></returns>
    public async Task<string> ReadTextFromImage(byte[] image)
    {
        var client = _httpFactory.CreateClient("AzureCognitiveAnalyzer");
        
        using var content = new ByteArrayContent(image);
        content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
        var response = await client.PostAsync("", content);

        if (response.StatusCode == HttpStatusCode.Accepted)
        {
            response.Headers.TryGetValues("Operation-Location", out var values);
            var operationLocation = values.FirstOrDefault();

            client = _httpFactory.CreateClient("AzureCognitiveAnalyzeResults");
            
            var model = new AzureOcrModel();
            
            while (model.Status != "succeeded")
            {
                if (response.StatusCode == HttpStatusCode.TooManyRequests) return string.Empty;
                
                response = await client.SendAsync(CreateAnalyzeResultsRequest(operationLocation));
                var str = await response.Content.ReadAsStringAsync();
                model = await response.Content.ReadFromJsonAsync<AzureOcrModel>();
            }

            return model.AnalyzeResult.ReadResults.First().Lines.First().Text;
        }

        return string.Empty;
    }

    private HttpRequestMessage CreateAnalyzeResultsRequest(string operationLocation)
    {
        return new HttpRequestMessage(HttpMethod.Get, operationLocation);
    }
}
