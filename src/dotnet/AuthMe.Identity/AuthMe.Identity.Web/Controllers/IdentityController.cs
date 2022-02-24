using AuthMe.Domain.Common.Api;
using AuthMe.Identity.Web.Filters;
using AuthMe.IdentityMsrv.Application.Identities.Commands.CreateIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Commands.DeleteIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;
using AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentityTrusted;
using AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;
using MediatR;
using Microsoft.AspNetCore.Authorization;
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
    
    [Authorize]
    [HttpGet("{id}", Name = "GetIdentity")]
    public async Task<IActionResult> GetIdentity(int id)
    {
        var query = new GetIdentityQuery { Id = id };
        var result = await _mediator.Send(query);

        if (result.Valid)
            return Ok(result);

        return NotFound(result);
    }
    
    [Authorize]
    [HttpGet(Name = "CreateIdentity")]
    public async Task<IActionResult> CreateIdentity()
    {
        var createIdentityCmd = new CreateIdentityCommand();
        var response = await _mediator.Send(createIdentityCmd);

        if (response.Valid)
            return Ok(new ValidatableResponse<int>(response.Result));

        return BadRequest(response);
    }

    [Authorize]
    [HttpPost("{id}", Name = "UpdateIdentity")]
    public async Task<IActionResult> UpdateIdentity(int id, [FromBody] UpdateIdentityCommand command)
    {
        command.Id = id;
        var response = await _mediator.Send(command);
        return Ok(response);
    }
    
    [Authorize]
    [HttpPost("/api/[controller]/trusted/{id}", Name = "UpdateIdentityTrusted")]
    public async Task<IActionResult> UpdateIdentityTrusted(int id, [FromBody] UpdateIdentityTrustedCommand command)
    {
        command.Id = id;
        var response = await _mediator.Send(command);
        return Ok(response);
    }
    
    [Authorize]
    [HttpDelete("{id}", Name = "DeleteIdentity")]
    public async Task<IActionResult> DeleteIdentity(int id)
    {
        var command = new DeleteIdentityCommand {Id = id};
        var response = await _mediator.Send(command);

        if (response.Valid)
            return Ok(response);
        return NotFound(response);
    }
}