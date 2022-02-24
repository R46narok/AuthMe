namespace AuthMe.Domain.Entities;

/// <summary>
/// Currently represents only Bulgarian identity documents.
/// </summary>
public class IdentityDocument
{
    public int Id { get; set; }
    
    public int IdentityId { get; set; }

    public byte[] DocumentFront { get; set; }

    public byte[] DocumentBack { get; set; }

    public string? OcrName { get; set; }
    public string? OcrMiddleName { get; set; }
    public string? OcrSurname { get; set; }
    public string? OcrDateOfBirth { get; set; }
    
    public IdentityDocument()
    {
        
    }

    public IdentityDocument(int identityId, byte[] documentFront, byte[] documentBack)
    {
        IdentityId = identityId;
        DocumentFront = documentFront;
        DocumentBack = documentBack;
    }
}