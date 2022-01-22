using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Queries.GetIdentity;

public class GetIdentityQuery : IRequest<ValidatableResponse<IdentityDto>>, IValidatable
{
    public int ExternalId { get; set; }
}

public class GetIdentityQueryHandler : IRequestHandler<GetIdentityQuery, ValidatableResponse<IdentityDto>>
{
    private readonly IMapper _mapper;
    private readonly IApplicationDbContext _dbContext;

    public GetIdentityQueryHandler(IMapper mapper, IApplicationDbContext dbContext)
    {
        _mapper = mapper;
        _dbContext = dbContext;
    }

    public async Task<ValidatableResponse<IdentityDto>> Handle(GetIdentityQuery request, CancellationToken cancellationToken)
    {
        var entry = _dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId);

        if (entry == null)
        {
            return new ValidatableResponse<IdentityDto>(null,
                new[] {"Requested Identity does not exist in the database."});
        }
        
        return new ValidatableResponse<IdentityDto>(_mapper.Map<IdentityDto>(entry));
    }
}