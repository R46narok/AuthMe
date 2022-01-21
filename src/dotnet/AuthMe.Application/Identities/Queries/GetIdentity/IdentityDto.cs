namespace AuthMe.Application.Identities.Queries.GetIdentity;

public class IdentityDto
{
    public string FirstName { get; set; }
    public string MiddleName { get; set; }
    public string LastName { get; set; }
    
    public DateOnly DateOfBirth { get; set; }
}