namespace AuthMe.Infrastructure.OcrService.Models;

public class AzureOcrReadResult
{
    public int Page { get; set; }
    public double Angle { get; set; }
    public int Width { get; set; }
    public int Height { get; set; }
    public string Unit { get; set; }
    public List<AzureOcrLine> Lines { get; set; }
}