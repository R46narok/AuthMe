namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

public class AzureOcrWord
{
    public List<int> BoundingBox { get; set; }
    public string Text { get; set; }
    public double Confidence { get; set; }
}