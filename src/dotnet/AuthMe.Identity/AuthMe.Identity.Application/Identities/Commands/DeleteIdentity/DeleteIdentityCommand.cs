using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;

public class DeleteIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int ExternalId { get; set; }
}

public class DeleteIdentityCommandHandler : IRequestHandler<DeleteIdentityCommand, ValidatableResponse>
{
    private readonly IIdentityDbContext _dbContext;

    public DeleteIdentityCommandHandler(IIdentityDbContext dbContext)
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