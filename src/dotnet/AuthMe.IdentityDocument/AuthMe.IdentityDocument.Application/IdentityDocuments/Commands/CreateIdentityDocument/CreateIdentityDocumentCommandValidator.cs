using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommandValidator : AbstractValidator<CreateIdentityDocumentCommand>
{
    public CreateIdentityDocumentCommandValidator()
    {
        RuleFor(x => x.Image).NotNull();
    }
}