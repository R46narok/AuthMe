namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

public class AzureOcrLine
{
    public string BoundingBox { get; set; }
    public List<AzureOcrWord> Words { get; set; }
}