using AuthMe.Domain.Entities;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

public class IdentityDocumentDto
{
    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    
    public IdentityProperty<DateTime> DateOfBirth { get; set; }

    public IdentityProperty<string> DocumentNumber { get; set; }
}