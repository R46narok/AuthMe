namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IIdentityDocumentValidityService
{
    public Task<bool> IsValidAsync(string documentNumber, DateTime dateOfBirth);
}