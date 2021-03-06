using System.Diagnostics.CodeAnalysis;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class UpdateIdentityCommandValidator : AbstractValidator<UpdateIdentityCommand>
{
    public UpdateIdentityCommandValidator(IIdentityRepository repository)
    {
        RuleFor(identity => identity.Id)
            .Must(repository.IdentityExists)
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}