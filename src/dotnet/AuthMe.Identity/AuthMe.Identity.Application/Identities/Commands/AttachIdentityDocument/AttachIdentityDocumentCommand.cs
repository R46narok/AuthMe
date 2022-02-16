using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.AttachIdentityDocument;

public class AttachIdentityDocumentCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int IdentityId { get; set; }
    public byte[] DocumentFront { get; set; }
    public byte[] DocumentBack { get; set; }
}

public class AttachIdentityDocumentCommandHandler : IRequestHandler<AttachIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IIdentityService _identityService;

    public AttachIdentityDocumentCommandHandler(IIdentityService identityService)
    {
        _identityService = identityService;
    }
    
    public async Task<ValidatableResponse> Handle(AttachIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        var response = await _identityService.AttachIdentityDocument(request.IdentityId, request.DocumentFront, request.DocumentFront);
        return new ValidatableResponse();
    }
}