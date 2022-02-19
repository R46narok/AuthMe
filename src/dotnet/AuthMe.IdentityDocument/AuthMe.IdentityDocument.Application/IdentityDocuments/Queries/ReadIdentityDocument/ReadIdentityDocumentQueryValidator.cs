using System.Data;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using FluentValidation;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class ReadIdentityDocumentQueryValidator : AbstractValidator<ReadIdentityDocumentQuery>
{
    public ReadIdentityDocumentQueryValidator(IIdentityDocumentRepository repository)
    {
        RuleFor(query => query.IdentityId)
            .MustAsync(async (identityId, _) => await repository.DocumentExistsAsync(identityId))
            .WithErrorCode("NotFound")
            .WithMessage("Document with the given identityId could not be found.");
    }
}