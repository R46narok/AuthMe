using AuthMe.Infrastructure.Async;

namespace AuthMe.Infrastructure.IdentityValidityService;

public class IdentityValidityBus : AzureBusSender<string>
{
    public IdentityValidityBus(string connectionString) : base(connectionString, "identity_validity")
    {
    }
}