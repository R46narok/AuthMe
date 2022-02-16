using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using AuthMe.IdentityService.Infrastructure.Grpc;
using Google.Protobuf;
using Grpc.Net.Client;

namespace AuthMe.IdentityMsrv.Infrastructure;

public class IdentityService : IIdentityService
{
    private readonly IdentityDocumentSrv.IdentityDocumentSrvClient _client;

    public IdentityService(string connection)
    {
        var channel = GrpcChannel.ForAddress(connection);
        _client = new IdentityDocumentSrv.IdentityDocumentSrvClient(channel);
    }

    public Task<IdentityDto> ReadIdentityDocument(byte[] document)
    {
        throw new NotImplementedException();
    }

    public async Task<int> AttachIdentityDocument(int identityId, byte[] documentFront, byte[] documentBack)
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