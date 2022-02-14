using AuthMe.Domain.Common;

namespace AuthMe.IdentityService.Application.Common.Interfaces;

public interface IIdentityValidityBus
{
    public Task Send<T>(Event<T> e);
}