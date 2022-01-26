namespace AuthMe.Application.Identities.Queries.GetIdentity;

public class IdentityDto
{
    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    
    public DateTime DateOfBirth { get; set; }
}