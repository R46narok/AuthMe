using AuthMe.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;

namespace AuthMe.IdentityService.Application.Common.Interfaces;

public interface IIdentityDbContext
{
    public DbSet<Identity> Identities { get; set; }

    public DatabaseFacade Database { get; }
    
    public int SaveChanges();
    public Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
}