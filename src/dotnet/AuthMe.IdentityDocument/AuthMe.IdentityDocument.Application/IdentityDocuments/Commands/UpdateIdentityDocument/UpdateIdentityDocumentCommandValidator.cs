using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.UpdateIdentityDocument;

public class UpdateIdentityDocumentCommandValidator : AbstractValidator<UpdateIdentityDocumentCommand>
{
    public UpdateIdentityDocumentCommandValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(command => command.IdentityId)
            .MustAsync(async (id, _) => await repository.DocumentExistsAsync(id))
            .WithErrorCode("NotFound")
            .WithMessage("Document with the given identityId could not be found.");
    }
}