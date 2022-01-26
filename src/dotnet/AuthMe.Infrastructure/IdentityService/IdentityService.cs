using System.Net.Http.Json;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.Infrastructure.IdentityService;

public class IdentityService : IIdentityService
{
    private readonly ILogger<IdentityService> _logger;
    private readonly IMediator _mediator;
    private readonly IHttpClientFactory _httpFactory;

    public IdentityService(ILogger<IdentityService> logger, IMediator mediator, IHttpClientFactory httpFactory)
    {
        _logger = logger;
        _mediator = mediator;
        _httpFactory = httpFactory;
    }
    
    public async Task<int> CreateIdentity(int externalId, byte[] document)
    {
        var createDocumentCmd = new CreateIdentityDocumentCommand
        {
            Image = document
        };

        var result = await _mediator.Send(createDocumentCmd);
        var client = _httpFactory.CreateClient("AzureCognitivePrediction");
        var request = new HttpRequestMessage(HttpMethod.Post, "");
        request.Content = new ByteArrayContent(document);

        var response = await client.SendAsync(request);
        if (response.IsSuccessStatusCode)
        {
            var model = await response.Content.ReadFromJsonAsync<AzurePredictionModel>();
            var successful = model.Predictions.Where(x => x.Probability > 0.9).ToArray();
        }
        
        return 123;
    }
}