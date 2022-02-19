using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;

public class DeleteIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }
}

public class DeleteIdentityCommandHandler : IRequestHandler<DeleteIdentityCommand, ValidatableResponse>
{
    private readonly IIdentityRepository _repository;
    private readonly ILogger<DeleteIdentityCommandHandler> _logger;

    public DeleteIdentityCommandHandler(IIdentityRepository repository, ILogger<DeleteIdentityCommandHandler> logger)
    {
        _repository = repository;
        _logger = logger;
    }
    
    public async Task<ValidatableResponse> Handle(DeleteIdentityCommand request, CancellationToken cancellationToken)
    {
        await _repository.DeleteIdentityAsync(request.Id);
        
        _logger.LogInformation("Deleted identity with id {Id}", request.Id);
        
        return new ValidatableResponse();
    }
}