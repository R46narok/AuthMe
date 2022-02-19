using System.Net;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text.Json;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService;

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
        var client = _httpFactory.CreateClient("AzureOcr");
        
        using var content = new ByteArrayContent(image);
        content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
        var response = await client.PostAsync("", content);

        var result = await response.Content.ReadFromJsonAsync<AzureOcrResult>();
        return result?.Regions[0].Lines[0].Words[0].Text ?? string.Empty;
    }

    private HttpRequestMessage CreateAnalyzeResultsRequest(string operationLocation)
    {
        return new HttpRequestMessage(HttpMethod.Get, operationLocation);
    }
}
