namespace AuthMe.Infrastructure.IdentityService.Models;

/// <summary>
/// A prediction result from Azure Cognitive Services - Custom Vision.
/// </summary>
public class AzurePrediction
{
    /// <summary>
    /// Confidence of the prediction.
    /// </summary>
    public double Probability { get; set; }
    public string TagId { get; set; }
    
    /// <summary>
    ///
    /// </summary>
    public string TagName { get; set; }
    public AzurePredictionBoundingBox<double> BoundingBox { get; set; }
}