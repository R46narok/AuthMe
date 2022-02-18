using FluentValidation;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommandValidator : AbstractValidator<CreateIdentityCommand>
{
    public CreateIdentityCommandValidator()
    {
    }
}