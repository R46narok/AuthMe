using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.ValidateIdentity;

public class ValidateIdentityCommand : IRequest<ValidatableResponse<bool>>, IValidatable
{
    /// <summary>
    /// A valid identity id
    /// </summary>
    public int IdentityId { get; set; }

    public byte[] DocumentFront { get; set; }

    public byte[] DocumentBack { get; set; }
}

// ReSharper disable once UnusedType.Global
public class ValidateIdentityCommandHandler : IRequestHandler<ValidateIdentityCommand, ValidatableResponse<bool>>
{
    private readonly IIdentityDbContext _dbContext;
    private readonly IMapper _mapper;

    public ValidateIdentityCommandHandler(IIdentityDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }
    
    public async Task<ValidatableResponse<bool>> Handle(ValidateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.IdentityId) != null)
            return new ValidatableResponse<bool>(false,
                new[] {"An Identity with that ExternalId already exists."});

        return new ValidatableResponse<bool>(true);
    }
}