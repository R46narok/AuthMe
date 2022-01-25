using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommand : IRequest<ValidatableResponse<int>>, IValidatable
{
    public int ExternalId { get; set; }
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityCommand, ValidatableResponse<int>>
{
    private readonly IApplicationDbContext _dbContext;
    private readonly IMapper _mapper;

    public CreateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }

    public async Task<ValidatableResponse<int>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        if (_dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId) != null)
            return new ValidatableResponse<int>(-1,
                new[] {"An Identity with that ExternalId already exists."});


        return new ValidatableResponse<int>(-1,
            new[] {"A new Identity record could not be created."});
    }
}