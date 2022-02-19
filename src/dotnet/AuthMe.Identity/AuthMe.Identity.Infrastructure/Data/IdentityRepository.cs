using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;

namespace AuthMe.IdentityMsrv.Infrastructure.Data;

public class IdentityRepository : IIdentityRepository
{
    private readonly IIdentityDbContext _dbContext;

    public IdentityRepository(IIdentityDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<int> CreateIdentityAsync(Identity identity)
    {
        var entry = _dbContext.Identities.Add(identity);
        await _dbContext.SaveChangesAsync();

        return entry.Entity.Id;
    }

    public async Task<Identity?> GetIdentityAsync(int id)
    {
        return await _dbContext.Identities.FindAsync(id);
    }

    public async Task<bool> IdentityExistsAsync(int id)
    {
        return await GetIdentityAsync(id) != null;
    }

    public async Task DeleteIdentityAsync(int id)
    {
        var identity = new Identity {Id = id};
        _dbContext.Identities.Remove(identity);
        await _dbContext.SaveChangesAsync();
    }

    public async Task UpdateIdentityAsync(Identity identity)
    {
        _dbContext.Identities.Update(identity);
        await _dbContext.SaveChangesAsync();
    }
}