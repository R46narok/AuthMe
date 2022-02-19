using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommand : IRequest<ValidatableResponse>
{
    public int IdentityId { get; set; }
}

public class DeleteIdentityDocumentCommandHandler : IRequestHandler<DeleteIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IIdentityDocumentRepository _repository;

    public DeleteIdentityDocumentCommandHandler(IIdentityDocumentRepository repository)
    {
        _repository = repository;
    }
    
    public async Task<ValidatableResponse> Handle(DeleteIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        await _repository.DeleteDocumentAsync(request.IdentityId);

        return new ValidatableResponse();
    }
}
