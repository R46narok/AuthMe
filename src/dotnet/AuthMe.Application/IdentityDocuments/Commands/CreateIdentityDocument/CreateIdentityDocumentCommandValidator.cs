using System.Data;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using FluentValidation;

namespace AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommandValidator : AbstractValidator<CreateIdentityDocumentCommand>
{
    public CreateIdentityDocumentCommandValidator()
    {
        RuleFor(x => x.Length).Must(x => x > 0);
        RuleFor(x => x.Image).NotNull();
    }
}