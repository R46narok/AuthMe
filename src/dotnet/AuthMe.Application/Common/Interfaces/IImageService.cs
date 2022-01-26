using AuthMe.Application.Common.Models;

namespace AuthMe.Application.Common.Interfaces;

public interface IImageService
{
    public Task<byte[]> CropAsync(byte[] bytes, int left, int top, int width, int height);

    public Task<byte[]> CropAsync(byte[] bytes,
        int firstLeft, int firstTop, int firstWidth, int firstHeight,
        int secondLeft, int secondTop, int secondWidth, int secondHeight);

    public Task<byte[]> ResizeAsync(byte[] bytes, int scaleFactor);

    public ImageMetadata ReadImageMetadata(byte[] bytes);
}