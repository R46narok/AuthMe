using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.Data;

public class IdentityDocumentDbContext : DbContext, IIdentityDocumentDbContext
{
    public DbSet<IdentityDocument> IdentityDocuments { get; set; }
    
    public IdentityDocumentDbContext(DbContextOptions<IdentityDocumentDbContext> options)
        : base(options)
    {
        
    }

}