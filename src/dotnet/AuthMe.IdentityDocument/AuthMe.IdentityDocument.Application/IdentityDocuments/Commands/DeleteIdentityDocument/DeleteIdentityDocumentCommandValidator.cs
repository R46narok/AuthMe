using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommandValidator : AbstractValidator<DeleteIdentityDocumentCommand>
{
    public DeleteIdentityDocumentCommandValidator()
    {
    }
}