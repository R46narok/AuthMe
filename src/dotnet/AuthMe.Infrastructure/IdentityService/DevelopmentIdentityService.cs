using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Queries.GetIdentity;
using AuthMe.Domain.Entities;

namespace AuthMe.Infrastructure.IdentityService;

public class DevelopmentIdentityService : IIdentityService
{
    public Task<IdentityDto> ReadIdentityDocument(byte[] document)
    {
        return Task.FromResult(new IdentityDto
        {
            Name = new IdentityProperty<string>() { Value = "Stanimir", IsValidated = true, LastUpdated = DateTime.Now},
            MiddleName = new IdentityProperty<string>() { Value = "Ivanov", IsValidated = false, LastUpdated = DateTime.Now},
            Surname = new IdentityProperty<string>() { Value = "Kolev", IsValidated = true, LastUpdated = DateTime.Now},
            DateOfBirth = new IdentityProperty<DateTime>() { Value = DateTime.Now, IsValidated = false, LastUpdated = DateTime.Now},
        });
    }
}