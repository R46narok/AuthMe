using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.ReadIdentityDocument;

namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

public interface IIdentityDocumentService
{
    public Task<IdentityDocumentDto> ReadIdentityDocument(byte[] document);
}