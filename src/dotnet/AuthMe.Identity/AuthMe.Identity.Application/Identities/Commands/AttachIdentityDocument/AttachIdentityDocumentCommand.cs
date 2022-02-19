using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.AttachIdentityDocument;

public class AttachIdentityDocumentCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int IdentityId { get; init; }
    public byte[]? DocumentFront { get; init; }
    public byte[]? DocumentBack { get; init; }
}

// ReSharper disable once UnusedType.Global
public class AttachIdentityDocumentCommandHandler : IRequestHandler<AttachIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IIdentityService _identityService;
    private readonly ILogger<AttachIdentityDocumentCommandHandler> _logger;

    public AttachIdentityDocumentCommandHandler(IIdentityService identityService, ILogger<AttachIdentityDocumentCommandHandler> logger)
    {
        _identityService = identityService;
        _logger = logger;
    }
    
    public async Task<ValidatableResponse> Handle(AttachIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        await _identityService.AttachIdentityDocument(request.IdentityId, request.DocumentFront, request.DocumentBack);
        _logger.LogInformation("Attached identity document to identity with id {IdentityId}", request.IdentityId);
        
        return new ValidatableResponse();
    }
}