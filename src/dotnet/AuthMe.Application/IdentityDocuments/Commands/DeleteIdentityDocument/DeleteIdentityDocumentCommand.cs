using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommand : IRequest<ValidatableResponse>
{
    public int Id { get; set; }
}

public class DeleteIdentityDocumentCommandHandler : IRequestHandler<DeleteIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IApplicationDbContext _dbContext;
    
    public DeleteIdentityDocumentCommandHandler(IApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public async Task<ValidatableResponse> Handle(DeleteIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        var entry = _dbContext.IdentityDocuments.FirstOrDefault(x => x.Id == request.Id);
        if (entry != null)
        {
            _dbContext.IdentityDocuments.Remove(entry);
            await _dbContext.SaveChangesAsync(cancellationToken);
        }

        return new ValidatableResponse(new[] {"Cannot delete a non existing IdentityDocument"});
    }
}
