namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

public class AzureOcrWord
{
    public string BoundingBox { get; set; }
    public string Text { get; set; }
}