using AuthMe.Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace AuthMe.Application.Common.Interfaces;

public interface IApplicationDbContext
{
    public DbSet<Identity> Identities { get; set; }

    public int SaveChanges();
    public Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
}