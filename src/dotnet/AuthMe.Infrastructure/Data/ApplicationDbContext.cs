using AuthMe.Application.Common.Interfaces;
using AuthMe.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace AuthMe.Infrastructure.Data;

/// <summary>
/// Illustrates database schema and connection.
/// Code-First techniques are used in this project.
/// </summary>
public class ApplicationDbContext : DbContext, IApplicationDbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options)
    {
        // TODO: not here
        Database.EnsureCreated();
    }
    
    public DbSet<Identity> Identities { get; set; }
    public DbSet<IdentityDocument> IdentityDocuments { get; set; }
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