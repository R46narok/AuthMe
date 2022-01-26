using System.Drawing;
using System.Globalization;
using System.Net.Http.Json;
using System.Net.Mime;
using System.Reflection;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Queries.GetIdentity;
using AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using GrapeCity.Documents.Imaging;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.Infrastructure.IdentityService;

public class IdentityService : IIdentityService
{
    private readonly ILogger<IdentityService> _logger;
    private readonly IImageService _imageService;
    private readonly IOcrService _ocrService;
    private readonly IHttpClientFactory _httpFactory;

    private Dictionary<string, AzurePredictionBoundingBox<int>> _matches;

    private static PropertyInfo[] _identityDtoProps;
    
    public const double Threshold = 0.95;
    public const int MinImageDimension = 50;
    
    public IdentityService(ILogger<IdentityService> logger, IImageService imageService, IOcrService ocrService, IHttpClientFactory httpFactory)
    {
        _logger = logger;
        _imageService = imageService;
        _ocrService = ocrService;
        _httpFactory = httpFactory;
        _matches = new Dictionary<string, AzurePredictionBoundingBox<int>>();

        // ReSharper disable once ConditionIsAlwaysTrueOrFalse
        if (_identityDtoProps == null)
            _identityDtoProps = typeof(IdentityDto).GetProperties();
    }

    public async Task<IdentityDto> ReadIdentityDocument(byte[] document)
    {
        var identity = new IdentityDto();
        
        var client = _httpFactory.CreateClient("AzureCognitivePrediction");
        var request = new HttpRequestMessage(HttpMethod.Post, "");
        request.Content = new ByteArrayContent(document);

        var response = await client.SendAsync(request);
        if (response.IsSuccessStatusCode)
        {
            var model = await response.Content.ReadFromJsonAsync<AzurePredictionModel>();
            var successful = model.Predictions.Where(x => x.Probability > Threshold).ToArray();

            foreach (var azurePrediction in successful)
                ProcessPrediction(document, azurePrediction);
            
            await PopulateIdentityDtoAsync(document, identity);
        }

        return identity;
    }

    private async Task PopulateIdentityDtoAsync(byte[] document, IdentityDto dto)
    {
        foreach (var prop in _identityDtoProps)
        {
            var name = prop.Name;
            
            var keyBox = _matches[$"{name}-Key"];
            var box = _matches[name];

            var value = await ProcessImage(document, box, keyBox);
            var text = await _ocrService.ReadTextFromImage(value);
            
            if (prop.Name == "DateOfBirth")
                prop.SetValue( dto, DateTime.ParseExact(text, "dd/MM/yyyy", CultureInfo.CurrentCulture));
            else
                prop.SetValue(dto, text.ToTitleCase());
        }
    }
    
    private void ProcessPrediction(byte[] document, AzurePrediction prediction)
    {
        var metadata = _imageService.ReadImageMetadata(document);
        var box = prediction.BoundingBox.ToPixelBased((int)metadata.Width, (int)metadata.Height);
        _matches.Add(prediction.TagName, box);
    }
    
    private async Task<byte[]> ProcessImage(byte[] document, AzurePredictionBoundingBox<int> box, AzurePredictionBoundingBox<int> keyBox)
    {
        var value = await _imageService.CropAsync(document,
            box.Left, box.Top, box.Width, box.Height,
            keyBox.Left, keyBox.Top, keyBox.Width, keyBox.Height);
        var metadata = _imageService.ReadImageMetadata(value);
            
        // resize if necessary
        int scaleFactor = 1;
        var determineScaleFactor = (int pixel) => MinImageDimension / pixel + 1;
            
        if (metadata.PixelWidth < MinImageDimension) 
            scaleFactor = determineScaleFactor(metadata.PixelWidth);
        if (metadata.PixelHeight < MinImageDimension && determineScaleFactor(metadata.PixelHeight) > scaleFactor)
            scaleFactor = determineScaleFactor(metadata.PixelHeight);

        if (scaleFactor > 1)
            value = await _imageService.ResizeAsync(value, scaleFactor);

        return value;
    }
}