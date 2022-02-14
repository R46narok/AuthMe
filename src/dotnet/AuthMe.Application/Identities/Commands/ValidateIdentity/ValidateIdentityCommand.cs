using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.ValidateIdentity;

public class ValidateIdentityCommand : IRequest<ValidatableResponse<bool>>, IValidatable
{
    /// <summary>
    /// A valid identity id
    /// </summary>
    public int IdentityId { get; set; }
    
    /// <summary>
    /// A valid id of associated identity document record.
    /// </summary>
    public int DocumentId { get; set; }
}

public class ValidateIdentityCommandHandler : IRequestHandler<ValidateIdentityCommand, ValidatableResponse<bool>>
{
    private readonly IApplicationDbContext _dbContext;
    private readonly IMapper _mapper;
    private readonly IIdentityService _identityService;

    public ValidateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper, IIdentityService identityService)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _identityService = identityService;
    }
    
    public async Task<ValidatableResponse<bool>> Handle(ValidateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.IdentityId) != null)
            return new ValidatableResponse<bool>(false,
                new[] {"An Identity with that ExternalId already exists."});

        var document = _dbContext.IdentityDocuments.FirstOrDefault(x => x.Id == request.DocumentId);
        if (document == null) return new ValidatableResponse<bool>(false,
            new[] { "A valid document could not be found." });
        
        var identityDto = await _identityService.ReadIdentityDocument(document.Image);

        
        
        return new ValidatableResponse<bool>(true);
    }
}