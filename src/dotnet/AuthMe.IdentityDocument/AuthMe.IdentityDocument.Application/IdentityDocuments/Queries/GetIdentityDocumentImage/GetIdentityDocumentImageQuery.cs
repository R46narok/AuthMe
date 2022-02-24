using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentImage;

public class GetIdentityDocumentImageQuery : IRequest<ValidatableResponse<byte[]>>
{
    public int IdentityId { get; set; }
    public DocumentSide Side { get; set; }
}

public class GetIdentityDocumentImageQueryHandler : IRequestHandler<GetIdentityDocumentImageQuery, ValidatableResponse<byte[]>>
{
    private readonly IIdentityDocumentRepository _repository;

    public GetIdentityDocumentImageQueryHandler(IIdentityDocumentRepository repository)
    {
        _repository = repository;
    }
    
    public async Task<ValidatableResponse<byte[]>> Handle(GetIdentityDocumentImageQuery request, CancellationToken cancellationToken)
    {
        var document = await _repository.GetDocument(request.IdentityId);

        if (request.Side == DocumentSide.Front)
            return new (document!.DocumentFront);

        return new(document!.DocumentBack);
    }
}