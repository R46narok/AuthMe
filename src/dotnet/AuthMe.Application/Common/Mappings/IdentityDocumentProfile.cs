
using AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.Domain.Entities;
using AutoMapper;

namespace AuthMe.Application.Common.Mappings;

public class IdentityDocumentProfile : Profile
{
    public IdentityDocumentProfile()
    {
        CreateMap<CreateIdentityDocumentCommand, IdentityDocument>();
    }
}