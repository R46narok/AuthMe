namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Models;

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