namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;

public class IdentityDocumentOcrDto
{
    public int IdentityId { get; set; }
    public string? Name { get; set; }
    public string? MiddleName { get; set; }
    public string? Surname { get; set; }
    public string? DateOfBirth { get; set; }
}