using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommand : IRequest<ValidatableResponse<int>>, IValidatable
{
    /// <summary>
    /// A valid id of the associated record in the Spring service.
    /// </summary>
    
    public int ExternalId { get; set; }

}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityCommand, ValidatableResponse<int>>
{
    private readonly IIdentityDbContext _dbContext;
    private readonly IMapper _mapper;
    private readonly IIdentityService _identityService;

    public CreateIdentityCommandHandler(IIdentityDbContext dbContext, IMapper mapper, IIdentityService identityService)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _identityService = identityService;
    }

    /// <summary>
    /// Performs OCR operations on the associated identity document.
    /// Creates an Identity entry based on the latter. 
    /// </summary>
    /// <param name="request">Fluent-Validated request</param>
    /// <param name="cancellationToken"></param>
    /// <returns>
    /// Id of the created entity.
    /// Errors:
    /// - An Identity with that ExternalId already exists.
    /// - A valid document could not be found.
    /// </returns>
    public async Task<ValidatableResponse<int>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId) != null)
            return new ValidatableResponse<int>(-1,
                new[] {"An Identity with that ExternalId already exists."});

        var identity = new Identity()
        {
            ExternalId = request.ExternalId,
            DateOfBirth = new IdentityProperty<DateTime>(),
            Name = new IdentityProperty<string>(),
            MiddleName = new IdentityProperty<string>(),
            Surname = new IdentityProperty<string>()
        };

        var entry = _dbContext.Identities.Add(identity);
        await _dbContext.SaveChangesAsync(cancellationToken);

        return new ValidatableResponse<int>(entry.Entity.Id);
    }
}