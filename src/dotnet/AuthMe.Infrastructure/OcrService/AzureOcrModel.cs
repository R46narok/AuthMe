public class AzureOcrStyle
{
    public string Name { get; set; }
    public double Confidence { get; set; }
}

public class AzureOcrAppearance
{
    public AzureOcrStyle Style { get; set; }
}

public class AzureOcrWord
{
    public List<int> BoundingBox { get; set; }
    public string Text { get; set; }
    public double Confidence { get; set; }
}

public class AzureOcrLine
{
    public List<int> BoundingBox { get; set; }
    public string Text { get; set; }
    public AzureOcrAppearance Appearance { get; set; }
    public List<AzureOcrWord> Words { get; set; }
}

public class AzureOcrReadResult
{
    public int Page { get; set; }
    public double Angle { get; set; }
    public int Width { get; set; }
    public int Height { get; set; }
    public string Unit { get; set; }
    public List<AzureOcrLine> Lines { get; set; }
}

public class AzureOcrAnalyzeResult
{
    public string Version { get; set; }
    public string ModelVersion { get; set; }
    public List<AzureOcrReadResult> ReadResults { get; set; }
}

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