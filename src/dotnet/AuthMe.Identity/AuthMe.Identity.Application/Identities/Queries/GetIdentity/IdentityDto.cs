using AuthMe.Domain.Entities;

namespace AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;

public class IdentityDto
{
    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    
    public IdentityProperty<DateTime> DateOfBirth { get; set; }
}