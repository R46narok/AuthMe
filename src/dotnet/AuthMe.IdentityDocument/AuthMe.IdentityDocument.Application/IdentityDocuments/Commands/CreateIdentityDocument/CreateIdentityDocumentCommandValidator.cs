using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommandValidator : AbstractValidator<CreateIdentityDocumentCommand>
{
    public CreateIdentityDocumentCommandValidator()
    {
        RuleFor(x => x.DocumentFront).NotNull();
        RuleFor(x => x.DocumentBack).NotNull();
    }
}