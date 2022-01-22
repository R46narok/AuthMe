using FluentValidation;

namespace AuthMe.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommandValidator : AbstractValidator<DeleteIdentityDocumentCommand>
{
    public DeleteIdentityDocumentCommandValidator()
    {
    }
}