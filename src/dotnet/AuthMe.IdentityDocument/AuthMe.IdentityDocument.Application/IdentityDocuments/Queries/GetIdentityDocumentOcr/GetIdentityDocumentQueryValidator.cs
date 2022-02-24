using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;

public class GetIdentityDocumentOcrQueryValidator : AbstractValidator<GetIdentityDocumentOcrQuery>
{
    public GetIdentityDocumentOcrQueryValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(query => query.IdentityId)
            .MustAsync(async (id, _) => await repository.DocumentExistsAsync(id))
            .WithErrorCode("NotFound")
            .WithMessage("Identity document not found.");
    }
}