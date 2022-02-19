using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.AttachIdentityDocument;

public class AttachIdentityDocumentCommandValidator : AbstractValidator<AttachIdentityDocumentCommand>
{
    public AttachIdentityDocumentCommandValidator(IIdentityRepository repository)
    {
        RuleFor(command => command.IdentityId)
            .MustAsync(async (identityId, _) => await repository.IdentityExistsAsync(identityId))
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}