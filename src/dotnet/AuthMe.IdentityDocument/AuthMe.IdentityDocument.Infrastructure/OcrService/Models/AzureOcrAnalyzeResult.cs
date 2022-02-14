namespace AuthMe.Infrastructure.OcrService.Models;

public class AzureOcrAnalyzeResult
{
    public string Version { get; set; }
    public string ModelVersion { get; set; }
    public List<AzureOcrReadResult> ReadResults { get; set; }
}