using System.Diagnostics.CodeAnalysis;
using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentityTrusted;

public class UpdateIdentityTrustedCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }

    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    public DateTime? DateOfBirth { get; set; }
}

[SuppressMessage("ReSharper", "UnusedType.Global")]
public class UpdateIdentityTrustedCommandHandler : IRequestHandler<UpdateIdentityTrustedCommand, ValidatableResponse>
{
    private readonly IIdentityRepository _repository;
    private readonly IMapper _mapper;
    private readonly ILogger<UpdateIdentityTrustedCommandHandler> _logger;

    public UpdateIdentityTrustedCommandHandler(IIdentityRepository repository, IMapper mapper,
        ILogger<UpdateIdentityTrustedCommandHandler> logger)
    {
        _repository = repository;
        _mapper = mapper;
        _logger = logger;
    }

    public async Task<ValidatableResponse> Handle(UpdateIdentityTrustedCommand request, CancellationToken cancellationToken)
    {
        var oldIdentity = await _repository.GetIdentityAsync(request.Id);
        
        InvalidateUpdatedFields(request.Name, oldIdentity!.Name);
        InvalidateUpdatedFields(request.MiddleName, oldIdentity.MiddleName);
        InvalidateUpdatedFields(request.Surname, oldIdentity.Surname);
        InvalidateUpdatedFields(request.DateOfBirth, oldIdentity.DateOfBirth);

        await _repository.UpdateIdentityAsync(oldIdentity);
        _logger.LogInformation("Updated trusted identity with id {Id}", request.Id);

        return new ValidatableResponse();
    }

    private void InvalidateUpdatedFields(string updated, IdentityProperty<string> old)
    {
        old.Validated = true;
        old.Value = updated;
    }

    private void InvalidateUpdatedFields(DateTime? updated, IdentityProperty<DateTime> old)
    {
        old.Validated = true;
        old.Value = updated!.Value;
    }
}