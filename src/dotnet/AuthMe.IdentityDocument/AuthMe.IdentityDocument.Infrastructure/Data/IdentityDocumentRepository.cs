using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.Data;

public class IdentityDocumentRepository : IIdentityDocumentRepository
{
    private readonly IIdentityDocumentDbContext _dbContext;

    public IdentityDocumentRepository(IIdentityDocumentDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public async Task<int> CreateDocumentAsync(IdentityDocument document)
    {
        var entry = _dbContext.IdentityDocuments.Add(document);
        await _dbContext.SaveChangesAsync();

        return entry.Entity.Id;
    }

    public async Task UpdateDocumentAsync(IdentityDocument document)
    {
        _dbContext.IdentityDocuments.Update(document);
        await _dbContext.SaveChangesAsync();
    }

    public async Task DeleteDocumentAsync(int identityId)
    {
        var document = await _dbContext.IdentityDocuments.FirstOrDefaultAsync(x => x.IdentityId == identityId);
        _dbContext.IdentityDocuments.Remove(document!);
        await _dbContext.SaveChangesAsync();
    }

    public async Task<IdentityDocument?> GetDocument(int identityId)
    {
        return await _dbContext.IdentityDocuments.FirstOrDefaultAsync(x => x.IdentityId == identityId);
    }

    public async Task<bool> DocumentExistsAsync(int identityId)
    {
        var document = await _dbContext.IdentityDocuments.FirstOrDefaultAsync(x => x.IdentityId == identityId);
        return document != null;
    }
}