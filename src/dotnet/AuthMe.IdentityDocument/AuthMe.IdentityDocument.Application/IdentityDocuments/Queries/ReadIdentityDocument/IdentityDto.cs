using AuthMe.Domain.Entities;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class IdentityDto
{
    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    
    public DateTime? DateOfBirth { get; set; }

    public string DocumentNumber { get; set; }
    
}