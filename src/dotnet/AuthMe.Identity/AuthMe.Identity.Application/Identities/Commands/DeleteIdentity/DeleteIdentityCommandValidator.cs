using System.Diagnostics.CodeAnalysis;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class DeleteIdentityCommandValidator : AbstractValidator<DeleteIdentityCommand>
{
    public DeleteIdentityCommandValidator(IIdentityDbContext dbContext)
    {
        RuleFor(identity => identity.Id)
            .Must(id => dbContext.Identities.Find(id) != null)
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}