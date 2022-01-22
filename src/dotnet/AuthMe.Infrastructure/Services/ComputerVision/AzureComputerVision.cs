using System.Net.Security;
using System.Text;
using AuthMe.Application.Common.Interfaces;
using Microsoft.Azure.CognitiveServices.Vision.ComputerVision;
using Microsoft.Azure.CognitiveServices.Vision.ComputerVision.Models;

namespace AuthMe.Infrastructure.Services.ComputerVision;

public class AzureComputerVision : IComputerVision
{
    private readonly ComputerVisionClient? _client;
    
    public AzureComputerVision(string endpoint, string subscriptionKey)
    {
        if (!string.IsNullOrWhiteSpace(endpoint) &&
            !string.IsNullOrWhiteSpace(subscriptionKey))
            _client = Authenticate(endpoint, subscriptionKey);
    }

    public async Task<List<string>> ReadFileUrl(string urlFile)
    {
        var textHeaders = await _client.ReadAsync(urlFile);
        string operationLocation = textHeaders.OperationLocation;

        const int numberOfCharsInOperationId = 36;
        string operationId = operationLocation.Substring(operationLocation.Length - numberOfCharsInOperationId);
        
        ReadOperationResult results;
        do
        {
            results = await _client.GetReadResultAsync(Guid.Parse(operationId));
        } while (results.Status is OperationStatusCodes.Running or OperationStatusCodes.NotStarted);
        
        var textUrlFileResults = results.AnalyzeResult.ReadResults;
        StringBuilder builder = new StringBuilder();

        List<string> lines = new List<string>();
        foreach (ReadResult page in textUrlFileResults)
        {
            lines.AddRange(page.Lines.Select(x => x.Text));
        }

        return lines;
    }

    private ComputerVisionClient Authenticate(string endpoint, string key)
    {
        ComputerVisionClient client =
            new ComputerVisionClient(new ApiKeyServiceClientCredentials(key))
                { Endpoint = endpoint };
        return client;
    }
}