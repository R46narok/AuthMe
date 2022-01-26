using AuthMe.Application.Identities.Queries.GetIdentity;

namespace AuthMe.Application.Common.Interfaces;

public interface IIdentityService
{
    public Task<IdentityDto> ReadIdentityDocument(byte[] document);
}