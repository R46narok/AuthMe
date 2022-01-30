using System.Text.Json;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Newtonsoft.Json;

namespace AuthMe.Infrastructure.Data;

/// <summary>
/// Illustrates database schema and connection.
/// Code-First techniques are used in this project.
/// </summary>
public class ApplicationDbContext : DbContext, IApplicationDbContext
{
        
    public DbSet<Identity> Identities { get; set; }
    public DbSet<IdentityDocument> IdentityDocuments { get; set; }
    
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options)
    {
        // TODO: not here
        Database.EnsureCreated();
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

/// <summary>
/// Converts DateOnly to DateTime and vice versa.
/// </summary>
public class DateOnlyConverter : ValueConverter<DateOnly, DateTime>
{
    /// <summary>
    /// Creates a new instance of this converter.
    /// </summary>
    public DateOnlyConverter() : base(
        d => d.ToDateTime(TimeOnly.MinValue),
        d => DateOnly.FromDateTime(d))
    { }
}