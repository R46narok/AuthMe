using FluentValidation;
using MediatR;

namespace AuthMe.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommandValidator : AbstractValidator<CreateIdentityCommand>
{
    public CreateIdentityCommandValidator()
    {
        RuleFor(x => x.ExternalId).Must(x => x > -1);
        RuleFor(x => x.DocumentId).Must(x => x > -1);
    }
}