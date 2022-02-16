using System.Text;
using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.IdentityDocumentService.Grpc;
using Google.Protobuf;
using Grpc.Core;
using MediatR;

namespace AuthMe.IdentityDocumentService.Web.Services;

public class IdentityDocumentService : IdentityDocumentSrv.IdentityDocumentSrvBase
{
    private readonly IMediator _mediator;
    private readonly IIdentityDocumentService _documentService;
    private readonly IIdentityDocumentValidityService _validityService;

    public IdentityDocumentService(IMediator mediator, IIdentityDocumentService documentService, IIdentityDocumentValidityService validityService)
    {
        _mediator = mediator;
        _documentService = documentService;
        _validityService = validityService;
    }

    public override async Task<CreateIdentityDocumentResponse> CreateIdentityDocument(CreateIdentityDocumentRequest request, ServerCallContext context)
    {
        // TODO: map
        var command = new CreateIdentityDocumentCommand
        {
            IdentityId = request.IdentityId,
            DocumentFront = request.DocumentFront.ToByteArray(),
            DocumentBack = request.DocumentBack.ToByteArray()
        };

        var response = await _mediator.Send(command);

        //var result = await _documentService.ReadIdentityDocument(command.DocumentFront);
        await _validityService.IsValidAsync("", DateTime.Now);
        return new CreateIdentityDocumentResponse() {Id = response.Result};
    }
}