﻿using System.Diagnostics.CodeAnalysis;
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

    public UpdateIdentityCommandHandler(IIdentityRepository repository, IMapper mapper, ILogger<UpdateIdentityCommandHandler> logger)
    {
        _repository = repository;
        _mapper = mapper;
        _logger = logger;
    }
    
    public async Task<ValidatableResponse> Handle(UpdateIdentityCommand request, CancellationToken cancellationToken)
    {
        var identity = _mapper.Map<Identity>(request);

        await _repository.UpdateIdentityAsync(identity);
        _logger.LogInformation("Updated identity with id {Id}", request.Id);
        
        return new ValidatableResponse();
    }
}