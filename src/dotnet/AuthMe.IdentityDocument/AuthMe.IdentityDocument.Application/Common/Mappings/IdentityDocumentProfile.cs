using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocument;
using AutoMapper;

namespace AuthMe.IdentityDocumentService.Application.Common.Mappings;

public class IdentityDocumentProfile : Profile
{
    public IdentityDocumentProfile()
    {
        CreateMap<CreateIdentityDocumentCommand, IdentityDocument>();
        CreateMap<IdentityDocument, IdentityDocumentDto>();
    }
}