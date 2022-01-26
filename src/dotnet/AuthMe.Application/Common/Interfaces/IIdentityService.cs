using AuthMe.Application.Identities.Queries.GetIdentity;

namespace AuthMe.Application.Common.Interfaces;

/// <summary>
/// An interface for identity operations outside the data and business layers. 
/// </summary>
public interface IIdentityService
{
    /// <summary>
    /// Converts an identity document to machine-readable information.
    /// </summary>
    /// <param name="document">An image of the identity document in PNG or JPEG format.</param>
    /// <returns>Null, if errors occured while processing the image.</returns>
    public Task<IdentityDto> ReadIdentityDocument(byte[] document);
}