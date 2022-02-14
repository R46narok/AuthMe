using FluentValidation;

namespace AuthMe.Application.Identities.Commands.ValidateIdentity;

public class ValidateIdentityCommandValidator : AbstractValidator<ValidateIdentityCommand>
{
    public ValidateIdentityCommandValidator()
    {
        
    }
}