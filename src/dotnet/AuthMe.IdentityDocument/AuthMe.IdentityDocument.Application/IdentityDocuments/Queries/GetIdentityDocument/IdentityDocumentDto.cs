namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocument;

public class IdentityDocumentDto
{
    public byte[] DocumentFront { get; set; }
    public byte[] DocumentBack { get; set; }
    
    public string? OcrName { get; set; }
    public string? OcrMiddleName { get; set; }
    public string? OcrSurname { get; set; }
    public string? OcrDateOfBirth { get; set; }
}