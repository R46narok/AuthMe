
using AuthMe.Application.Common.Models;
using AuthMe.Domain.Entities;

namespace AuthMe.Application.Common.Interfaces;

public interface IIdentityDocumentReader
{
    public Task<IdentityDocumentModel> ReadIdentityDocumentAsync(string url);
}