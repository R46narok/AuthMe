using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Application.Identities.Queries.GetIdentity;
using AuthMe.Domain.Entities;
using AutoMapper;

namespace AuthMe.Application.Common.Mappings;

public class IdentityProfile : Profile
{
    public IdentityProfile()
    {
        CreateMap<CreateIdentityCommand, Identity>();
        CreateMap<Identity, IdentityDto>();
        CreateMap<IdentityDto, Identity>();
    }
}