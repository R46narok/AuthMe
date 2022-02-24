using AuthMe.Domain.Entities;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;
using AutoMapper;

namespace AuthMe.IdentityDocumentService.Application.Common.Mappings;

public class IdentityDocumentProfile : Profile
{
    public IdentityDocumentProfile()
    {
        CreateMap<CreateIdentityDocumentCommand, IdentityDocument>();

        CreateMap<IdentityDocument, IdentityDocumentOcrDto>()
            .ForMember(x => x.Name, y => y.MapFrom(z => z.OcrName))
            .ForMember(x => x.MiddleName, y => y.MapFrom(z => z.OcrMiddleName))
            .ForMember(x => x.Surname, y => y.MapFrom(z => z.OcrSurname))
            .ForMember(x => x.DateOfBirth, y => y.MapFrom(z => z.OcrDateOfBirth));
    }
}