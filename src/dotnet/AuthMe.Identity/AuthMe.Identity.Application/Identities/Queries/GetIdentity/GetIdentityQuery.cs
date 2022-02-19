using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;

public class GetIdentityQuery : IRequest<ValidatableResponse<IdentityDto>>, IValidatable
{
    public int Id { get; set; }
}

public class GetIdentityQueryHandler : IRequestHandler<GetIdentityQuery, ValidatableResponse<IdentityDto>>
{
    private readonly IMapper _mapper;
    private readonly IIdentityDbContext _dbContext;

    public GetIdentityQueryHandler(IMapper mapper, IIdentityDbContext dbContext)
    {
        _mapper = mapper;
        _dbContext = dbContext;
    }

    /// <summary>
    /// Gets an Identity from the database.
    /// </summary>
    /// <param name="request">Fluent-Validated request</param>
    /// <param name="cancellationToken"></param>
    /// <returns>
    /// An Identity, if present.
    /// Errors:
    /// - Requested Identity does not exist in the database.
    /// </returns>
    public async Task<ValidatableResponse<IdentityDto>> Handle(GetIdentityQuery request, CancellationToken cancellationToken)
    {
        var entry = _dbContext.Identities.FirstOrDefault(x => x.Id == request.Id);

        if (entry == null)
        {
            return new ValidatableResponse<IdentityDto>(null,
                new[] {"Requested Identity does not exist in the database."});
        }
        
        return new ValidatableResponse<IdentityDto>(_mapper.Map<IdentityDto>(entry));
    }
}