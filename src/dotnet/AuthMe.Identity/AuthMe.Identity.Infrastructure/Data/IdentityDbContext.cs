using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace AuthMe.IdentityMsrv.Infrastructure.Data;

/// <summary>
/// Illustrates database schema and connection.
/// Code-First techniques are used in this project.
/// </summary>
public class IdentityDbContext : DbContext, IIdentityDbContext
{
    public DbSet<Identity> Identities { get; set; }
    public DbSet<IdentityDocument> IdentityDocuments { get; set; }
    
    public IdentityDbContext(DbContextOptions<IdentityDbContext> options)
        : base(options)
    { 
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Identity>().Property(p => p.Name)
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