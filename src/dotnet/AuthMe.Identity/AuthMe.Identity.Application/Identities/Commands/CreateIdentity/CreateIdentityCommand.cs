using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.CreateIdentity;

public class CreateIdentityCommand : IRequest<ValidatableResponse<int>>, IValidatable
{
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityCommand, ValidatableResponse<int>>
{
    private readonly IIdentityRepository _repository;
    private readonly ILogger<CreateIdentityCommandHandler> _logger;

    public CreateIdentityCommandHandler(IIdentityRepository repository, ILogger<CreateIdentityCommandHandler> logger)
    {
        _repository = repository;
        _logger = logger;
    }
    
    public async Task<ValidatableResponse<int>> Handle(CreateIdentityCommand request, CancellationToken cancellationToken)
    {
        var identity = new Identity();
        identity.Name.Validated = true;
        identity.MiddleName.Validated = true;
        identity.Surname.Validated = true;
        identity.DateOfBirth.Validated = true;
        var id = await _repository.CreateIdentityAsync(identity);

        _logger.LogInformation("Created identity with the new id of {Id}", id);
        
        return new ValidatableResponse<int>(id);
    }
}