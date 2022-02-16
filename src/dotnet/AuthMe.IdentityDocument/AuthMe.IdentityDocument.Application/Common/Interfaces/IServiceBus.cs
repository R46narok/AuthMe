using AuthMe.Domain.Common;

namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IServiceBus
{
    public Task Send<T>(Event<T> e, string queue);
}