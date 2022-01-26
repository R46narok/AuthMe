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
    private readonly IIdentityService _identityService;

    public CreateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper, IIdentityService identityService)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _identityService = identityService;
    }

    public async Task<ValidatableResponse<int>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId) != null)
            return new ValidatableResponse<int>(-1,
                new[] {"An Identity with that ExternalId already exists."});

        var document = _dbContext.IdentityDocuments.FirstOrDefault(x => x.Id == request.DocumentId);
        if (document == null) return new ValidatableResponse<int>(-1,
                new[] { "A valid document could not be found." });
        
        var identityDto = await _identityService.ReadIdentityDocument(document.Image);
        var identity = _mapper.Map<Identity>(identityDto);

        var entry = _dbContext.Identities.Add(identity);
        await _dbContext.SaveChangesAsync(cancellationToken);

        return new ValidatableResponse<int>(entry.Entity.Id);
    }
}