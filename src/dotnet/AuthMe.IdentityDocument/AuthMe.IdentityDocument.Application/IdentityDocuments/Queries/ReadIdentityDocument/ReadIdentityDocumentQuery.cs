using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class ReadIdentityDocumentQuery : IRequest<ValidatableResponse<IdentityDto>>
{
    public int IdentityId { get; set; }
}

public class ReadIdentityDocumentQueryHandler : IRequestHandler<ReadIdentityDocumentQuery, ValidatableResponse<IdentityDto>>
{
    private readonly IIdentityDocumentRepository _repository;
    private readonly IIdentityDocumentService _identityDocumentService;
    private readonly IIdentityDocumentValidityService _documentValidityService;

    public ReadIdentityDocumentQueryHandler(IIdentityDocumentRepository repository
        , IIdentityDocumentService identityDocumentService, IIdentityDocumentValidityService documentValidityService)
    {
        _repository = repository;
        _identityDocumentService = identityDocumentService;
        _documentValidityService = documentValidityService;
    }
    
    public async Task<ValidatableResponse<IdentityDto>> Handle(ReadIdentityDocumentQuery request, CancellationToken cancellationToken)
    {
        var document = await _repository.GetDocument(request.IdentityId);

        var documentDto = await _identityDocumentService.ReadIdentityDocument(document.DocumentFront);

        if (!string.IsNullOrEmpty(documentDto.DocumentNumber) && documentDto.DateOfBirth.HasValue)
        {
            var valid = await _documentValidityService.IsValidAsync(documentDto.DocumentNumber, documentDto.DateOfBirth.Value.ToString("dd.MM.yyyy"));

            if (!valid)
                return new ValidatableResponse<IdentityDto>(documentDto, new[] {"Document is not valid."});
        }
        
        return new ValidatableResponse<IdentityDto>(documentDto);
    }
}