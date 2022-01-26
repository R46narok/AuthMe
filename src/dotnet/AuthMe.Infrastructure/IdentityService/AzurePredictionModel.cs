// ReSharper disable ClassNeverInstantiated.Global
// ReSharper disable UnusedAutoPropertyAccessor.Global
#pragma warning disable CS8618
namespace AuthMe.Infrastructure.IdentityService;

/// <summary>
/// Bounding box of a prediction.
/// AzurePredictionBoundingBox<double> is used with ratio values.
/// AzurePredictionBoundingBox<int> is used with pixel values.
/// </summary>
/// <typeparam name="T">Ratio or pixel values</typeparam>
public class AzurePredictionBoundingBox<T> 
    where T : struct
{
    public T Left { get; set; }
    public T Top { get; set; }
    public T Width { get; set; }
    public T Height { get; set; }
}

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

/// <summary>
/// The whole response from Azure Cognitive Services - Custom Vision v3.0.
/// </summary>
public class AzurePredictionModel
{
    /// <summary>
    /// Id of the prediction in Azure.
    /// </summary>
    public string Id { get; set; }
    
    /// <summary>
    /// Id of the project in Azure.
    /// </summary>
    public string Project { get; set; }
    
    /// <summary>
    /// Id of the iteration of the neural network.
    /// </summary>
    public string Iteration { get; set; }
    
    public DateTime Created { get; set; }
    public AzurePrediction[] Predictions { get; set; }
}