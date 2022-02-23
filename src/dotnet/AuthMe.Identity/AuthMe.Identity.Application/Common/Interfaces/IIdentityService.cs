using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;

namespace AuthMe.IdentityMsrv.Application.Common.Interfaces;

/// <summary>
/// An interface for identity operations outside the data and business layers. 
/// </summary>
public interface IIdentityService
{
    /// <summary>
    /// Attaches the front and rear part of an identity document to a particular identity.
    /// </summary>
    /// <param name="identityId">A valid identity id in the Identity microservice database.</param>
    /// <param name="documentFront">Image of the front of the document in bytes.</param>
    /// <param name="documentBack">Image of the rear of the document in bytes.</param>
    /// <returns></returns>
    public Task<int> AttachIdentityDocument(int identityId, byte[]? documentFront, byte[]? documentBack);
    

}