using System.Net.Http.Json;
using System.Text.Json;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Application.Identities.Commands.AttachIdentityDocument;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using AuthMe.IdentityMsrv.Infrastructure.Settings;
using AuthMe.IdentityService.Infrastructure.Grpc;
using Google.Protobuf;
using Grpc.Net.Client;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityMsrv.Infrastructure;

class AttachIdentityDocumentResponse
{
    public int Id { get; set; }
}

public class IdentityService : IIdentityService
{
    private readonly HttpClient _client;

    public IdentityService(IHttpClientFactory httpFactory)
    {
        _client = httpFactory.CreateClient("IdentityDocument");
    }

    public async Task<int> AttachIdentityDocument(int identityId, byte[]? documentFront, byte[]? documentBack)
    {
        var command = new AttachIdentityDocumentCommand
        {
            IdentityId = identityId,
            DocumentFront = documentFront,
            DocumentBack = documentBack
        };

        var request = new HttpRequestMessage(HttpMethod.Post, "/api/identitydocument");
        request.Content = JsonContent.Create(command);
        
        var response = await _client.SendAsync(request);
        var model = response.Content.ReadFromJsonAsync<AttachIdentityDocumentResponse>();
        
        return model.Id;
    }
}