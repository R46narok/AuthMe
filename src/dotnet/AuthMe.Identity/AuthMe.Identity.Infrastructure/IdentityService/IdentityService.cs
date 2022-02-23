using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using AuthMe.IdentityMsrv.Infrastructure.Settings;
using AuthMe.IdentityService.Infrastructure.Grpc;
using Google.Protobuf;
using Grpc.Net.Client;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityMsrv.Infrastructure;

public class IdentityService : IIdentityService
{
    private readonly IdentityDocumentSrv.IdentityDocumentSrvClient _client;

    public IdentityService(IOptions<IdentityServiceSettings> options)
    {
        var channel = GrpcChannel.ForAddress(options.Value.Endpoint);
        _client = new IdentityDocumentSrv.IdentityDocumentSrvClient(channel);
    }

    public async Task<int> AttachIdentityDocument(int identityId, byte[]? documentFront, byte[]? documentBack)
    {
        var request = new CreateIdentityDocumentRequest
        {
            IdentityId = identityId,
            DocumentFront = ByteString.CopyFrom(documentFront),
            DocumentBack = ByteString.CopyFrom(documentBack)
        };

        var response = await _client.CreateIdentityDocumentAsync(request);
        return response.Id;
    }
}