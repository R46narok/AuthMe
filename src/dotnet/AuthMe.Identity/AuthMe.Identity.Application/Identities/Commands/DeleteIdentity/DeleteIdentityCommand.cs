using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;

public class DeleteIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }
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
        var identity = new Identity {Id = request.Id};
        
        _dbContext.Identities.Remove(identity);
        await _dbContext.SaveChangesAsync(cancellationToken);
        
        return new ValidatableResponse();
    }
}