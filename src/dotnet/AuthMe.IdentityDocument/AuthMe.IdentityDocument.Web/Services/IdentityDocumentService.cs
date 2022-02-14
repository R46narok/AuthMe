using System.Text;
using Authme;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Google.Protobuf;
using Grpc.Core;
using MediatR;

namespace AuthMe.IdentityDocumentService.Web.Services;

public class IdentityDocumentService : IdentityDocument.IdentityDocumentBase
{
    private readonly IMediator _mediator;

    public IdentityDocumentService(IMediator mediator, IIdentityValidityService validityService)
    {
        _mediator = mediator;
    }
    
    public override async Task<GetIdentityDocumentResponse> GetIdentityDocument(GetIdentityDocumentRequest request, ServerCallContext context)
    {
        return new GetIdentityDocumentResponse {Id = 123, Image = ByteString.Empty};
    }
}