using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace AuthMe.IdentityDocumentService.Infrastructure.Data;

public class IdentityDocumentDbContext : DbContext, IIdentityDocumentDbContext
{
        
    public DbSet<Domain.Entities.Identity> Identities { get; set; }
    public DbSet<IdentityDocument> IdentityDocuments { get; set; }
    
    public IdentityDocumentDbContext(DbContextOptions<IdentityDocumentDbContext> options)
        : base(options)
    {
        
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Domain.Entities.Identity>().Property(p => p.Name)
            .HasConversion(
                v => JsonConvert.SerializeObject(v),
                v => JsonConvert.DeserializeObject<IdentityProperty<string>>(v));
        
        modelBuilder.Entity<Identity>().Property(p => p.MiddleName)
            .HasConversion(
                v => JsonConvert.SerializeObject(v),
                v => JsonConvert.DeserializeObject<IdentityProperty<string>>(v));
        
        modelBuilder.Entity<Identity>().Property(p => p.Surname)
            .HasConversion(
                v => JsonConvert.SerializeObject(v),
                v => JsonConvert.DeserializeObject<IdentityProperty<string>>(v));
        
        modelBuilder.Entity<Identity>().Property(p => p.DateOfBirth)
            .HasConversion(
                v => JsonConvert.SerializeObject(v),
                v => JsonConvert.DeserializeObject<IdentityProperty<DateTime>>(v));
    }
}