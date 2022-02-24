using AuthMe.Domain.Entities;

namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IIdentityDocumentRepository
{
    public Task<int> CreateDocumentAsync(IdentityDocument document);
    public Task UpdateDocumentAsync(IdentityDocument document);
    public Task DeleteDocumentAsync(int identityId);

    public Task<IdentityDocument?> GetDocument(int identityId);
    
    public Task<bool> DocumentExistsAsync(int identityId);
}