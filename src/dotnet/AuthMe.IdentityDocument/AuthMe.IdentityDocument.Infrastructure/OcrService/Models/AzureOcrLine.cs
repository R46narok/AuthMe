namespace AuthMe.Infrastructure.OcrService.Models;

public class AzureOcrLine
{
    public List<int> BoundingBox { get; set; }
    public string Text { get; set; }
    public AzureOcrAppearance Appearance { get; set; }
    public List<AzureOcrWord> Words { get; set; }
}