using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommand : IRequest<ValidatableResponse<ApiResponse<bool>>>, IValidatable, IRequest<bool>
{
    public string FirstName { get; set; }
    public string MiddleName { get; set; }
    public string LastName { get; set; }
    
    public DateOnly DateOfBirth { get; set; }
}


public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityCommand, ValidatableResponse<ApiResponse<bool>>>
{
    private readonly IApplicationDbContext _dbContext;
    private readonly IMapper _mapper;

    public CreateIdentityCommandHandler(IApplicationDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }

    public async Task<ValidatableResponse<ApiResponse<bool>>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        var entry = _mapper.Map<Identity>(request);
        _dbContext.Identities.Add(entry);
        
        var savedEntries = await _dbContext.SaveChangesAsync(cancellationToken);

        return new ValidatableResponse<ApiResponse<bool>>(new ApiResponse<bool>
        {
            Data = true,
            Outcome = ApiOperationOutcome.SuccessfulOutcome
        });
    }
}