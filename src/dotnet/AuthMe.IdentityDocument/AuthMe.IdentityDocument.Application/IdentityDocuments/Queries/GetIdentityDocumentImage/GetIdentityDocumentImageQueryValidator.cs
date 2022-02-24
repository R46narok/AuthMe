using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentImage;

public class GetIdentityDocumentImageQueryValidator : AbstractValidator<GetIdentityDocumentImageQuery>
{
    public GetIdentityDocumentImageQueryValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(command => command.IdentityId)
            .MustAsync(async (identityId, _) => await repository.DocumentExistsAsync(identityId))
            .WithErrorCode("NotFound")
            .WithMessage("Document with the given identityId could not be found.");
    }
}