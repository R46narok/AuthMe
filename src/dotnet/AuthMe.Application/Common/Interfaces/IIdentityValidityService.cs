namespace AuthMe.Application.Common.Interfaces;

public interface IIdentityValidityService
{
    public bool IsValid(string documentNumber, DateTime dateOfBirth);
}