using System.Diagnostics.CodeAnalysis;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class UpdateIdentityCommandValidator : AbstractValidator<DeleteIdentityCommand>
{
    public UpdateIdentityCommandValidator(IIdentityRepository repository)
    {
        RuleFor(identity => identity.Id)
            .MustAsync(async (id,_) => await repository.IdentityExistsAsync(id))
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}