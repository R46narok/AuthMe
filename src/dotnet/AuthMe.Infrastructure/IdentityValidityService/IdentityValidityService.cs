using AuthMe.Application.Common.Interfaces;

namespace AuthMe.Infrastructure.IdentityValidityService;

public class IdentityValidityService : IIdentityValidityService
{
    public bool IsValid(string documentNumber, DateTime dateOfBirth)
    {
        throw new NotImplementedException();
    }
}