namespace AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Models;

// Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);

public class AzureOcrResult
{
    public string Language { get; set; }
    public double TextAngle { get; set; }
    public string Orientation { get; set; }
    public List<AzureOcrRegion> Regions { get; set; }
    public string ModelVersion { get; set; }
}

