using AuthMe.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;

namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IIdentityDocumentDbContext
{
    public DbSet<Identity>? Identities { get; set; }
    public DbSet<IdentityDocument>? IdentityDocuments { get; set; }

    public DatabaseFacade Database { get; }
    
    public int SaveChanges();
    public Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
}