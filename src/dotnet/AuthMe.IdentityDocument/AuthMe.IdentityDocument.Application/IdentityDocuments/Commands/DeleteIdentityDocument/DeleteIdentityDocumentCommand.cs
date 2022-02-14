using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.DeleteIdentityDocument;

public class DeleteIdentityDocumentCommand : IRequest<ValidatableResponse>
{
    public int Id { get; set; }
}

public class DeleteIdentityDocumentCommandHandler : IRequestHandler<DeleteIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IIdentityDocumentDbContext _dbContext;
    
    public DeleteIdentityDocumentCommandHandler(IIdentityDocumentDbContext dbContext)
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
