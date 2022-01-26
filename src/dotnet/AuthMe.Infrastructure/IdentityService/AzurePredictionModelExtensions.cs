namespace AuthMe.Infrastructure.IdentityService;

public static class AzurePredictionBoundingBoxExtensions 
{
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