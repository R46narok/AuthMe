using System.Diagnostics.CodeAnalysis;
using System.Reflection;
using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;

public class UpdateIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }

    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    public IdentityProperty<DateTime> DateOfBirth { get; set; }
}

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class UpdateIdentityCommandHandler : IRequestHandler<UpdateIdentityCommand, ValidatableResponse>
{
    private readonly IIdentityRepository _repository;
    private readonly IMapper _mapper;
    private readonly ILogger<UpdateIdentityCommandHandler> _logger;

    public UpdateIdentityCommandHandler(IIdentityRepository repository, IMapper mapper,
        ILogger<UpdateIdentityCommandHandler> logger)
    {
        _repository = repository;
        _mapper = mapper;
        _logger = logger;
    }

    public async Task<ValidatableResponse> Handle(UpdateIdentityCommand request, CancellationToken cancellationToken)
    {
        var identity = _mapper.Map<Identity>(request);

        //var oldIdentity = await _repository.GetIdentityAsync(request.Id);
        // invalidateUpdatedFields(identity, oldIdentity);

        await _repository.UpdateIdentityAsync(identity);
        _logger.LogInformation("Updated identity with id {Id}", request.Id);

        return new ValidatableResponse();
    }

    private void invalidateUpdatedFields(Identity identity, Identity oldIdentity)
    {
        if (identity.Name != null && !identity.Name.Equals(oldIdentity.Name))
        {
            identity.Name.Validated = false;
        }
        else if (identity.Name == null)
        {
            identity.Name.Validated = true;
        }
        
        if (identity.MiddleName != null && !identity.MiddleName.Equals(oldIdentity.MiddleName))
        {
            identity.MiddleName.Validated = false;
        }
        else if (identity.MiddleName == null)
        {
            identity.MiddleName.Validated = true;
        }
        
        if (identity.Surname != null && !identity.Surname.Equals(oldIdentity.Surname))
        {
            identity.Surname.Validated = false;
        }
        else if (identity.Surname == null)
        {
            identity.Surname.Validated = true;
        }
        
        if (identity.DateOfBirth != null && !identity.DateOfBirth.Equals(oldIdentity.DateOfBirth))
        {
            identity.DateOfBirth.Validated = false;
        }
        else if (identity.DateOfBirth == null)
        {
            identity.DateOfBirth.Validated = true;
        }
    }
}