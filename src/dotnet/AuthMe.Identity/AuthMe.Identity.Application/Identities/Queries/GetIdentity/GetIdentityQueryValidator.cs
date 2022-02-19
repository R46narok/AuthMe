using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;

public class GetIdentityQueryValidator : AbstractValidator<GetIdentityQuery>
{
    public GetIdentityQueryValidator(IIdentityRepository repository)
    {
        RuleFor(command => command.Id)
            .MustAsync(async (id, _) => await repository.IdentityExistsAsync(id))
            .WithErrorCode("NotFound")
            .WithMessage("Identity with the given id does not exist in the database.");
    }
}