using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Models;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Extensions;

public static class AzurePredictionBoundingBoxExtensions 
{
    /// <summary>
    /// Converts a ratio-based bounding box to a pixel-based bounding box.
    /// </summary>
    /// <param name="box">A ratio-based bounding box.</param>
    /// <param name="imageWidth">Width of the whole image.</param>
    /// <param name="imageHeight">Height of the whole image.</param>
    /// <returns>Pixel-based bounding box</returns>
    public static AzurePredictionBoundingBox<int> ToPixelBased(this AzurePredictionBoundingBox<double> box, int imageWidth, int imageHeight)
    {
        return new AzurePredictionBoundingBox<int>
        {
            Left = (int)(box.Left * imageWidth),
            Top = (int)(box.Top * imageHeight),
            Width = (int)(box.Width * imageWidth),
            Height = (int)(box.Height * imageHeight)
        };
    }
}