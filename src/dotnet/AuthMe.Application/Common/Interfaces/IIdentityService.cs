namespace AuthMe.Application.Common.Interfaces;

public interface IIdentityService
{
    public Task<int> CreateIdentity(int externalId, byte[] document);
}