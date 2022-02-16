using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class ReadIdentityDocumentQuery : IRequest<ValidatableResponse<IdentityDocumentDto>>
{
    public int DocumentId { get; set; }
}

public class ReadIdentityDocumentQueryHandler : IRequestHandler<ReadIdentityDocumentQuery, ValidatableResponse<IdentityDocumentDto>>
{
    private readonly IIdentityDocumentDbContext _dbContext;

    public ReadIdentityDocumentQueryHandler(IIdentityDocumentDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public async Task<ValidatableResponse<IdentityDocumentDto>> Handle(ReadIdentityDocumentQuery request, CancellationToken cancellationToken)
    {
        return new ValidatableResponse<IdentityDocumentDto>(new IdentityDocumentDto());
    }
}