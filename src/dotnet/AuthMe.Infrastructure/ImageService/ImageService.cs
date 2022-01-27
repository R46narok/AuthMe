using System.Drawing;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Common.Models;
using GrapeCity.Documents.Imaging;
using Microsoft.Extensions.Logging;

namespace AuthMe.Infrastructure.ImageService;

/// <summary>
/// A cross-platform implementation of <see cref="IImageService"/>
/// </summary>
public class ImageService : IImageService
{
    private readonly ILogger<ImageService> _logger;

    public ImageService(ILogger<ImageService> logger)
    {
        _logger = logger;
    }
    
    public async Task<byte[]> CropAsync(byte[] bytes, int left, int top, int width, int height)
    {
        var bmp = new GcBitmap();
        bmp.Load(bytes);
        
        var rectangle = new Rectangle(left, top, width, height);
        var cropped = bmp.Clip(rectangle);

        var stream = cropped.ToJpegStream(100);
        var length = stream.Length;
        var result = new byte[length];

        await stream.ReadAsync(result, 0, (int)length);
        stream.Close();

        return result;
    }
    
    // Removes a smaller boundbox from a bigger boundbox
    public async Task<byte[]> CropAsync(byte[] bytes,
        int firstLeft, int firstTop, int firstWidth, int firstHeight, 
        int secondLeft, int secondTop, int secondWidth, int secondHeight)
    {
        int left, top, width, height;
        
        if (firstWidth > secondWidth)
        {
            left = secondLeft + secondWidth;
            top = firstTop;
            width = firstWidth - secondWidth + 20;
            height = firstHeight;

            return await CropAsync(bytes, left, top, width, height);
        }

        left = (secondLeft + secondWidth) - (firstLeft + firstWidth);
        top = secondTop;
        width = secondWidth - firstWidth +20 ;
        height = secondHeight;

        return await CropAsync(bytes, left, top, width, height);
    }

    public async Task<byte[]> ResizeAsync(byte[] bytes, int scaleFactor)
    {
        var bmp = new GcBitmap();
        bmp.Load(bytes);
        var resized = bmp.Resize(bmp.PixelWidth * scaleFactor, bmp.PixelHeight * scaleFactor);
        
        var stream = resized.ToJpegStream(100);
        var length = stream.Length;
        var result = new byte[length];

        await stream.ReadAsync(result, 0, (int)length);
        stream.Close();
        return result;
    }
    
    public ImageMetadata ReadImageMetadata(byte[] bytes)
    {
        var bmp = new GcBitmap();
        bmp.Load(bytes);
        
        var metadata = new ImageMetadata
        {
            Width = bmp.Width,
            Height = bmp.Height,
            PixelWidth = bmp.PixelWidth,
            PixelHeight = bmp.PixelHeight
        };

        return metadata;
    }

}