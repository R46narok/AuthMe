using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;

public class GetIdentityDocumentOcrQuery : IRequest<ValidatableResponse<IdentityDocumentOcrDto>>
{
    public int IdentityId { get; set; }
}

public class GetIdentityDocumentOcrQueryHandler : IRequestHandler<GetIdentityDocumentOcrQuery, ValidatableResponse<IdentityDocumentOcrDto>>
{
    private readonly IIdentityDocumentRepository _repository;
    private readonly IMapper _mapper;

    public GetIdentityDocumentOcrQueryHandler(IIdentityDocumentRepository repository, IMapper mapper)
    {
        _repository = repository;
        _mapper = mapper;
    }
    
    public async Task<ValidatableResponse<IdentityDocumentOcrDto>> Handle(GetIdentityDocumentOcrQuery request, CancellationToken cancellationToken)
    {
        var document = await _repository.GetDocument(request.IdentityId);
        var dto = _mapper.Map<IdentityDocumentOcrDto>(document);
        
        return new ValidatableResponse<IdentityDocumentOcrDto>(dto);
    }
}