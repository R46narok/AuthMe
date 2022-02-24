namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IOcrValidityService
{
    public Task<long> NextIdentityId();
}