using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommandValidator : AbstractValidator<DeleteIdentityDocumentCommand>
{
    public DeleteIdentityDocumentCommandValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(command => command.IdentityId)
            .MustAsync(async (identityId, _) => await repository.DocumentExistsAsync(identityId))
            .WithErrorCode("NotFound")
            .WithMessage("Document with the given identityId could not be found.");
    }
}