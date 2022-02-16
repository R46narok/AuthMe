namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

/// <summary>
/// The whole response from Azure Cognitive Services - OCR v3.0
/// </summary>
public class AzureOcrModel
{
    /// <summary>
    /// "running" or "succeeded"
    /// </summary>
    public string Status { get; set; }
    public DateTime CreatedDateTime { get; set; }
    public DateTime LastUpdatedDateTime { get; set; }
    public AzureOcrAnalyzeResult AnalyzeResult { get; set; }
}