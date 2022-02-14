using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommand : IRequest<ValidatableResponse<int>>
{
    /// <summary>
    /// In-memory content of an identity document image.
    /// PNG or JPEG
    /// </summary>
    public byte[] Image { get; set; }
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityDocumentCommand, ValidatableResponse<int>>
{
    private readonly IIdentityDocumentDbContext _dbContext;
    private readonly IMapper _mapper;
    
    public CreateIdentityCommandHandler(IIdentityDocumentDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }
    
    /// <summary>
    /// Adds an identity document to the database.
    /// </summary>
    /// <param name="request">Fluent-Validated request</param>
    /// <param name="cancellationToken"></param>
    /// <returns>
    /// The id of the new entry.
    /// </returns>
    public async Task<ValidatableResponse<int>> Handle(CreateIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        var document = _mapper.Map<IdentityDocument>(request);
        var entry = _dbContext.IdentityDocuments.Add(document);
        await _dbContext.SaveChangesAsync(cancellationToken);

        return new ValidatableResponse<int>(entry.Entity.Id);
    }
}