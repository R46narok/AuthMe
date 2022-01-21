namespace AuthMe.Domain.Entities;

// TODO: Maybe extend
public class Identity
{
    public int Id { get; set; }
    public int ExternalId { get; set; }
    
    public string FirstName { get; set; }
    public string MiddleName { get; set; }
    public string LastName { get; set; }
    
    public DateOnly DateOfBirth { get; set; }
}