namespace AuthMe.Domain.Entities;

public class IdentityDocument
{
    public int Id { get; set; }
    public int Length { get; set; }
    public byte[] Image { get; set; }
}