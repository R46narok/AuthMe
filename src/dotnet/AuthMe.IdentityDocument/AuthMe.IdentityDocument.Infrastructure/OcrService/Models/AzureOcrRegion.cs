namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

public class AzureOcrRegion
{
    public string BoundingBox { get; set; }
    public List<AzureOcrLine> Lines { get; set; }
}