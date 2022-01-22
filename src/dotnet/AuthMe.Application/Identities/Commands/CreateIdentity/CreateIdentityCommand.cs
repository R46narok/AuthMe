using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommand : IRequest<ValidatableResponse<int>>, IValidatable
{
    public int ExternalId { get; set; }
    public int DocumentId { get; set; }
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityCommand, ValidatableResponse<int>>
{
    private readonly IApplicationDbContext _dbContext;
    private readonly IMapper _mapper;
    private readonly IIdentityDocumentReader _documentReader;

    public CreateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper, IIdentityDocumentReader documentReader)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _documentReader = documentReader;
    }

    public async Task<ValidatableResponse<int>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId) != null)
            return new ValidatableResponse<int>(-1,
                new[] {"An Identity with that ExternalId already exists."});
        
        var model = await _documentReader.ReadIdentityDocumentAsync(@$"https://ip/api/identitydocument/{request.DocumentId}");
        var entry = _mapper.Map<Identity>(model);
        
        var entity = _dbContext.Identities.Add(entry);
        
        var savedEntries = await _dbContext.SaveChangesAsync(cancellationToken);
        
        if (savedEntries > 0)
            return new ValidatableResponse<int>(entity.Entity.Id);
        
        return new ValidatableResponse<int>(-1,
            new[] {"A new Identity record could not be created."});
    }
}