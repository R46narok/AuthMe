using System.Globalization;
using System.Net.Http.Json;
using System.Reflection;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Extensions;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Models;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;

/// <summary>
/// Implements the IIdentityService interface with Azure Cognitive Services.
/// </summary>
/// <seealso cref="IIdentityService"/>
public class IdentityDocumentService : IIdentityDocumentService
{
    private readonly ILogger<IdentityDocumentService> _logger;
    private readonly IImageService _imageService;
    private readonly IOcrService _ocrService;
    private readonly IHttpClientFactory _httpFactory;

    private readonly Dictionary<string, AzurePredictionBoundingBox<int>> _matches;

    /// <summary>
    /// Caches the reflection of IdentityDto to achieve a
    /// greater performance (Transient services).
    /// </summary>
    private static PropertyInfo[] _identityDtoProps;

    /// <summary>
    /// A minimum probability value to be achieved to ensure
    /// better confidence in results.
    /// </summary>
    private const double Threshold = 0.90;
    
    /// <summary>
    /// Azure Cognitive Services require images that have dimensions
    /// at least 50x50 pixels.
    /// </summary>
    /// <see cref="https://centraluseuap.dev.cognitive.microsoft.com/docs/services/computer-vision-v3-2/operations/5d986960601faab4bf452005"/>
    private const int MinImageDimension = 50;
    
    public IdentityDocumentService(ILogger<IdentityDocumentService> logger, 
        IImageService imageService, IOcrService ocrService, IHttpClientFactory httpFactory)
    {
        _logger = logger;
        _imageService = imageService;
        _ocrService = ocrService;
        _httpFactory = httpFactory;
        _matches = new Dictionary<string, AzurePredictionBoundingBox<int>>();

        // ReSharper disable once ConditionIsAlwaysTrueOrFalse
        if (_identityDtoProps == null)
            _identityDtoProps = typeof(IdentityDocumentDto).GetProperties();
    }

    /// <summary>
    /// Converts an identity document to machine-readable information.
    /// Sends a request to Azure Cognitive Prediction Service to acquire key-value pairs on the identity document
    /// and filters predictions by Threshold.
    /// The results are properly transformed to meet Azure's requirements and populates the model.
    /// </summary>
    /// <param name="document">An identity document to extract information from.</param>
    /// <returns>Empty Dto, if errors occur.</returns>
    public async Task<IdentityDocumentDto> ReadIdentityDocument(byte[] document)
    {
        var identity = new IdentityDocumentDto();
        
        var client = _httpFactory.CreateClient("AzureCognitivePrediction");
        var request = new HttpRequestMessage(HttpMethod.Post, "");
        request.Content = new ByteArrayContent(document);

        var response = await client.SendAsync(request);
        if (response.IsSuccessStatusCode)
        {
            var model = await response.Content.ReadFromJsonAsync<AzurePredictionModel>();
            var successful = model!.Predictions.Where(x => x.Probability > Threshold).ToArray();

            foreach (var azurePrediction in successful)
                ProcessPrediction(document, azurePrediction);
            
            identity = await PopulateIdentityDtoAsync(document);
        }

        return identity;
    }

    /// <summary>
    /// Converts the prediction from a ratio-based to a pixel-based and
    /// adds it in the cache.
    /// </summary>
    /// <param name="document">An image to use its dimensions.</param>
    /// <param name="prediction">Prediction to be processed.</param>
    private void ProcessPrediction(byte[] document, AzurePrediction prediction)
    {
        var metadata = _imageService.ReadImageMetadata(document);
        var box = prediction.BoundingBox.ToPixelBased((int)metadata.Width, (int)metadata.Height);
        _matches.Add(prediction.TagName, box);
    }

    /// <summary>
    /// Begins the information extraction from filtered and processed predictions.
    /// Sets the appropriate format of the values.
    /// </summary>
    /// <param name="document">An image to extract from.</param>
    /// <returns>Populated dto.</returns>
    private async Task<IdentityDocumentDto> PopulateIdentityDtoAsync(byte[] document)
    {
        var dto = new IdentityDocumentDto();
        
        foreach (var prop in _identityDtoProps)
        {
            var name = prop.Name;

            var type = prop.PropertyType;
            object? instance = null;
            
            if (_matches.ContainsKey($"{name}-Key") && _matches.ContainsKey(name))
            {
                var keyBox = _matches[$"{name}-Key"];
                var box = _matches[name];

                var value = await ProcessImage(document, box, keyBox); // value image
                var text =  await _ocrService.ReadTextFromImage(value); // OCR
                
                if (!string.IsNullOrWhiteSpace(text))
                {
                    try
                    {
                        if (prop.Name == "DateOfBirth")
                            instance = Activator.CreateInstance(type,
                                DateTime.ParseExact(text, "dd.MM.yyyy", CultureInfo.CurrentCulture));
                        else
                            instance = new string(text.ToTitleCase());
                    }
                    catch (Exception e)
                    {
                        _logger.LogError("Parsing data went wrong.");
                    }
                }
            }
            
            prop.SetValue(dto, instance);
        }

        return dto;
    }
    

    /// <summary>
    /// Extracts value from a key-value pair in an image format(PNG or JPEG) and ensures
    /// it is at least 50x50 pixels. 
    /// </summary>
    /// <param name="document">The whole identity document.</param>
    /// <param name="box">The bounding box of the key-value pair.</param>
    /// <param name="keyBox">The bounding box of the key.</param>
    /// <returns>At least 50x50 pixels image, containing the value.</returns>
    private async Task<byte[]> ProcessImage(byte[] document, AzurePredictionBoundingBox<int> box, AzurePredictionBoundingBox<int> keyBox)
    {
        // Extracts the value from key-value pair
        var value = await _imageService.CropAsync(document,
            box.Left, box.Top, box.Width, box.Height,
            keyBox.Left, keyBox.Top, keyBox.Width, keyBox.Height);
        var metadata = _imageService.ReadImageMetadata(value);
        
        int scaleFactor = 1;
        var determineScaleFactor = (int pixel) => MinImageDimension / pixel + 1;
            
        // how much to scale
        if (metadata.PixelWidth < MinImageDimension) 
            scaleFactor = determineScaleFactor(metadata.PixelWidth);
        if (metadata.PixelHeight < MinImageDimension && determineScaleFactor(metadata.PixelHeight) > scaleFactor)
            scaleFactor = determineScaleFactor(metadata.PixelHeight);

        // resize if necessary
        if (scaleFactor > 1)
            value = await _imageService.ResizeAsync(value, scaleFactor);

        return value;
    }
}