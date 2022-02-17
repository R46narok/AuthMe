using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class ReadIdentityDocumentQuery : IRequest<ValidatableResponse<IdentityDocumentDto>>
{
    public int IdentityId { get; set; }
}

public class ReadIdentityDocumentQueryHandler : IRequestHandler<ReadIdentityDocumentQuery, ValidatableResponse<IdentityDocumentDto>>
{
    private readonly IIdentityDocumentDbContext _dbContext;
    private readonly IIdentityDocumentService _identityDocumentService;
    private readonly IIdentityDocumentValidityService _documentValidityService;

    public ReadIdentityDocumentQueryHandler(IIdentityDocumentDbContext dbContext
        , IIdentityDocumentService identityDocumentService, IIdentityDocumentValidityService documentValidityService)
    {
        _dbContext = dbContext;
        _identityDocumentService = identityDocumentService;
        _documentValidityService = documentValidityService;
    }
    
    public async Task<ValidatableResponse<IdentityDocumentDto>> Handle(ReadIdentityDocumentQuery request, CancellationToken cancellationToken)
    {
        var document = await _dbContext.IdentityDocuments.FirstOrDefaultAsync(x => x.IdentityId == request.IdentityId
            , cancellationToken: cancellationToken);
        if (document == null)
            return new ValidatableResponse<IdentityDocumentDto>(null, new[] {$"Could not find an identity document with the given id {request.IdentityId}"});

        var documentDto = await _identityDocumentService.ReadIdentityDocument(document.DocumentFront);

        if (documentDto.DocumentNumber.IsValidated && documentDto.DateOfBirth.IsValidated)
        {
            var valid = await _documentValidityService.IsValidAsync(documentDto.DocumentNumber.Value, documentDto.DateOfBirth.Value);

            if (!valid)
                return new ValidatableResponse<IdentityDocumentDto>(documentDto, new[] {"Document is not valid."});
        }
        
        return new ValidatableResponse<IdentityDocumentDto>(documentDto);
    }
}