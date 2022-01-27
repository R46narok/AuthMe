namespace AuthMe.Domain.Entities;

/// <summary>
/// Currently represents only Bulgarian identity documents.
/// </summary>
public class IdentityDocument
{
    public int Id { get; set; }
    
    /// <summary>
    /// Image of the identity document in PNG or JPEG.
    /// </summary>
    public byte[] Image { get; set; }
}