using System.Diagnostics.CodeAnalysis;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class DeleteIdentityCommandValidator : AbstractValidator<DeleteIdentityCommand>
{
    public DeleteIdentityCommandValidator(IIdentityRepository repository)
    {
        RuleFor(command => command.Id)
            .MustAsync(async (id, _) => await repository.IdentityExistsAsync(id))
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}