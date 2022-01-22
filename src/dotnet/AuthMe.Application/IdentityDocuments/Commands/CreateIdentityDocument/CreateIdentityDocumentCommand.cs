using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommand : IRequest<ValidatableResponse<int>>
{
    public int Length { get; set; }
    public byte[] Image { get; set; }
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityDocumentCommand, ValidatableResponse<int>>
{
    private readonly IApplicationDbContext _dbContext;
    private readonly IMapper _mapper;
    
    public CreateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }
    
    public async Task<ValidatableResponse<int>> Handle(CreateIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        var document = _mapper.Map<IdentityDocument>(request);
        var entry = _dbContext.IdentityDocuments.Add(document);
        await _dbContext.SaveChangesAsync(cancellationToken);

        return new ValidatableResponse<int>(entry.Entity.Id);
    }
}