using AuthMe.Domain.Common;
using AuthMe.Domain.Entities;

namespace AuthMe.IdentityMsrv.Application.Common.Interfaces;

public interface IIdentityRepository
{
    /// <summary>
    /// 
    /// </summary>
    /// <returns></returns>
    public Task<int> CreateIdentityAsync(Identity identity);
    
    /// <summary>
    /// 
    /// </summary>
    /// <param name="id"></param>
    /// <returns>null, if the identity is not present in the database.</returns>
    public Task<Identity?> GetIdentityAsync(int id);

    /// <summary>
    /// 
    /// </summary>
    /// <param name="id"></param>
    /// <returns></returns>
    public Task<bool> IdentityExistsAsync(int id);

    /// <summary>
    /// 
    /// </summary>
    /// <param name="id"></param>
    /// <returns></returns>
    public Task DeleteIdentityAsync(int id);

    public Task UpdateIdentityAsync(Identity identity);
}