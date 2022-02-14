using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Events;
using AuthMe.IdentityService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.ValidateIdentity;

public class ValidateIdentityCommand : IRequest<ValidatableResponse<bool>>, IValidatable
{
    /// <summary>
    /// A valid identity id
    /// </summary>
    public int IdentityId { get; set; }
}

public class ValidateIdentityCommandHandler : IRequestHandler<ValidateIdentityCommand, ValidatableResponse<bool>>
{
    private readonly IIdentityDbContext _dbContext;
    private readonly IMapper _mapper;
    private readonly IIdentityValidityBus _bus;

    public ValidateIdentityCommandHandler(IIdentityDbContext dbContext, IMapper mapper, IIdentityValidityBus bus)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _bus = bus;
    }
    
    public async Task<ValidatableResponse<bool>> Handle(ValidateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.IdentityId) != null)
            return new ValidatableResponse<bool>(false,
                new[] {"An Identity with that ExternalId already exists."});

        var e = new ValidateIdentityEvent(request.IdentityId);
        await _bus.Send(e);

        return new ValidatableResponse<bool>(true);
    }
}