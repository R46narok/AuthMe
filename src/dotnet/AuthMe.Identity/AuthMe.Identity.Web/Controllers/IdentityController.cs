﻿using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Identities.Commands.CreateIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.Identity.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityController : ControllerBase
{
    private readonly IMediator _mediator;

    public IdentityController(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    [HttpGet(template: "{externalId}")]
    public async Task<ActionResult<ValidatableResponse<IdentityDto>>> GetIdentity(int externalId)
    {
        var query = new GetIdentityQuery {ExternalId = externalId};
        var result = await _mediator.Send(query);

        if (result.IsValid)
            return Ok(result);

        return NotFound(result);
    }
    
    [HttpPost(Name = "CreateIdentity")]
    public async Task<ActionResult<ValidatableResponse<int>>> CreateIdentity([FromForm] int externalId)
    {
        var createIdentityCmd = new CreateIdentityCommand { ExternalId = externalId};
        var response = await _mediator.Send(createIdentityCmd);

        if (response.IsValid)
            return Ok(new ValidatableResponse<int>(response.Result));

        return BadRequest(response);
    }

    [HttpPut]
    public async Task<ActionResult<ValidatableResponse>> UpdateIdentity([FromBody] UpdateIdentityCommand command)
    {
        var response = await _mediator.Send(command);
        return Ok(response);
    }

    [HttpDelete(template: "{externalId}")]
    public async Task<ActionResult<ValidatableResponse>> DeleteIdentity(int externalId)
    {
        var command = new DeleteIdentityCommand {ExternalId = externalId};
        var response = await _mediator.Send(command);

        if (response.IsValid)
            return Ok(response);
        return NotFound(response);
    }
}