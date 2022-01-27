using AuthMe.Application.Common.Models;
// ReSharper disable InvalidXmlDocComment

namespace AuthMe.Application.Common.Interfaces;

/// <summary>
/// An interface for image transformations.
/// </summary>
public interface IImageService
{
    /// <summary>
    /// Crops a bounding box from an image. Requires bounding box's dimensions in pixels.
    /// </summary>
    /// <param name="bytes">An image to crop from.</param>
    /// <returns>Cropped image.</returns>
    public Task<byte[]> CropAsync(byte[] bytes, int left, int top, int width, int height);

    /// <summary>
    /// Crops a bounding box from a bigger bounding box from an image. Requires bounding boxes' dimensions
    /// in pixels.
    /// </summary>
    /// <param name="bytes">An image to crop from.</param>
    /// <returns>Cropped image.</returns>
    public Task<byte[]> CropAsync(byte[] bytes,
        int firstLeft, int firstTop, int firstWidth, int firstHeight,
        int secondLeft, int secondTop, int secondWidth, int secondHeight);

    /// <summary>
    /// Resizes an image, but preserves its proportions. 
    /// </summary>
    /// <param name="bytes">An image to be scaled.</param>
    /// <param name="scaleFactor">A scale factor for both width and height.</param>
    /// <returns>Resized image.</returns>
    public Task<byte[]> ResizeAsync(byte[] bytes, int scaleFactor);

    /// <summary>
    /// Reads basic metadata from an in-memory image.
    /// </summary>
    /// <param name="bytes">An image.</param>
    /// <returns>Metadata.</returns>
    public ImageMetadata ReadImageMetadata(byte[] bytes);
}