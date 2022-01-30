using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.DeleteIdentity;

public class DeleteIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int ExternalId { get; set; }
}

public class DeleteIdentityCommandHandler : IRequestHandler<DeleteIdentityCommand, ValidatableResponse>
{
    private readonly IApplicationDbContext _dbContext;

    public DeleteIdentityCommandHandler(IApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public async Task<ValidatableResponse> Handle(DeleteIdentityCommand request, CancellationToken cancellationToken)
    {
        var identity = _dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId);
        
        if (identity == null)
            return new ValidatableResponse(new [] { $"Identity with external id {request.ExternalId} could not be found." });

        _dbContext.Identities.Remove(identity);
        await _dbContext.SaveChangesAsync(cancellationToken);
        
        return new ValidatableResponse();
    }
}