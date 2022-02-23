using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocument;

public class GetIdentityDocumentQuery : IRequest<ValidatableResponse<IdentityDocumentDto>>
{
    public int IdentityId { get; set; }
}

public class GetIdentityDocumentQueryHandler : IRequestHandler<GetIdentityDocumentQuery, ValidatableResponse<IdentityDocumentDto>>
{
    private readonly IIdentityDocumentRepository _repository;
    private readonly IMapper _mapper;

    public GetIdentityDocumentQueryHandler(IIdentityDocumentRepository repository, IMapper mapper)
    {
        _repository = repository;
        _mapper = mapper;
    }
    
    public async Task<ValidatableResponse<IdentityDocumentDto>> Handle(GetIdentityDocumentQuery request, CancellationToken cancellationToken)
    {
        var document = await _repository.GetDocument(request.IdentityId);
        var dto = _mapper.Map<IdentityDocumentDto>(document);
        
        return new ValidatableResponse<IdentityDocumentDto>(dto);
    }
}