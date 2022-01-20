using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.Application.Identities.Queries.GetIdentity;

public class GetIdentityQuery : IRequest<ValidatableResponse<ApiResponse<IdentityDto>>>, IValidatable
{
    public int ExternalId { get; set; }
}

public class GetIdentityQueryHandler : IRequestHandler<GetIdentityQuery, ValidatableResponse<ApiResponse<IdentityDto>>>
{
    private readonly IApplicationDbContext _dbContext;

    public GetIdentityQueryHandler(IApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public Task<ValidatableResponse<ApiResponse<IdentityDto>>> Handle(GetIdentityQuery request, CancellationToken cancellationToken)
    {
        return null;
    }
}