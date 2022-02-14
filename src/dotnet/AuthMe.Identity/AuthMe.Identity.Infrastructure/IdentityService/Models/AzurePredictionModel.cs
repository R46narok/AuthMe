// ReSharper disable ClassNeverInstantiated.Global
// ReSharper disable UnusedAutoPropertyAccessor.Global
#pragma warning disable CS8618
namespace AuthMe.Infrastructure.IdentityService.Models;

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