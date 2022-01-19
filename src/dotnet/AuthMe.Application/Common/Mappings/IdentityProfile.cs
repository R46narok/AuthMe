using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Domain.Entities;
using AutoMapper;

namespace AuthMe.Application.Common.Mappings;

public class IdentityProfile : Profile
{
    public IdentityProfile()
    {
        CreateMap<CreateIdentityCommand, Identity>();
    }
}