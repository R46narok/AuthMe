using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Identities.Commands.CreateIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using AutoMapper;

namespace AuthMe.IdentityMsrv.Application.Common.Mappings;

public class IdentityProfile : Profile
{
    public IdentityProfile()
    {
        CreateMap<CreateIdentityCommand, Identity>();
        CreateMap<Identity, IdentityDto>();
        CreateMap<IdentityDto, Identity>();
    }
}