namespace AuthMe.IdentityDocumentService.Application.Common.Models;

public class ImageMetadata
{
    public double Width { get; set; }
    public double Height { get; set; }
    
    /// <summary>
    /// Preferred to use, but may not be the same as Width on some platforms.
    /// </summary>
    public int PixelWidth { get; set; }
    
    /// <summary>
    /// Preferred to use, but may not be the same as Width on some platforms.
    /// </summary>
    public int PixelHeight { get; set; }
}