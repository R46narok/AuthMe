using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommandValidator : AbstractValidator<CreateIdentityDocumentCommand>
{
    public CreateIdentityDocumentCommandValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(command => command.IdentityId)
            .MustAsync(async (identityId, _) => !(await repository.DocumentExistsAsync(identityId)))
            .WithErrorCode("AlreadyExists")
            .WithMessage("Document is already attached to this identity.");
        
        RuleFor(x => x.DocumentFront).NotNull();
        RuleFor(x => x.DocumentBack).NotNull();
    }
}