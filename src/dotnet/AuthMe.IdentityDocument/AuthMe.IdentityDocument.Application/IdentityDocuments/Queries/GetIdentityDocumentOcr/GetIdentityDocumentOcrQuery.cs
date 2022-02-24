using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;

public class GetIdentityDocumentOcrQuery : IRequest<ValidatableResponse<IdentityDocumentOcrDto>>
{
}

public class GetIdentityDocumentOcrQueryHandler : IRequestHandler<GetIdentityDocumentOcrQuery, ValidatableResponse<IdentityDocumentOcrDto>>
{
    private readonly IIdentityDocumentRepository _repository;
    private readonly IMapper _mapper;
    private readonly IOcrValidityService _ocrValidityService;

    public GetIdentityDocumentOcrQueryHandler(IIdentityDocumentRepository repository, IMapper mapper, IOcrValidityService ocrValidityService)
    {
        _repository = repository;
        _mapper = mapper;
        _ocrValidityService = ocrValidityService;
    }
    
    public async Task<ValidatableResponse<IdentityDocumentOcrDto>> Handle(GetIdentityDocumentOcrQuery request, CancellationToken cancellationToken)
    {
        var id = (int)await _ocrValidityService.NextIdentityId();

        if (id == -1)
            return new (null, new[] {"No documents in the queue"});
        if (!await _repository.DocumentExistsAsync(id))
            return new (null, new[] {"Document with this identityId does not exist"});
        
        var document = await _repository.GetDocument(id);
        var dto = _mapper.Map<IdentityDocumentOcrDto>(document);

        dto.IdentityId = id;
        
        return new ValidatableResponse<IdentityDocumentOcrDto>(dto);
    }
}