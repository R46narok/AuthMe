using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AutoMapper;

namespace AuthMe.IdentityDocumentService.Application.Common.Mappings;

public class IdentityDocumentProfile : Profile
{
    public IdentityDocumentProfile()
    {
        CreateMap<CreateIdentityDocumentCommand, IdentityDocument>();
    }
}