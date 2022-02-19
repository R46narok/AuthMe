using AuthMe.Domain.Common.Api;
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
    
    [HttpGet("{id}", Name = "GetIdentity")]
    public async Task<ActionResult<ValidatableResponse<IdentityDto>>> GetIdentity(int id)
    {
        var query = new GetIdentityQuery { Id = id };
        var result = await _mediator.Send(query);

        if (result.Valid)
            return Ok(result);

        return NotFound(result);
    }
    
    [HttpGet(Name = "CreateIdentity")]
    public async Task<ActionResult<ValidatableResponse<int>>> CreateIdentity()
    {
        var createIdentityCmd = new CreateIdentityCommand();
        var response = await _mediator.Send(createIdentityCmd);

        if (response.Valid)
            return Ok(new ValidatableResponse<int>(response.Result));

        return BadRequest(response);
    }

    [HttpPost(Name = "UpdateIdentity")]
    public async Task<ActionResult<ValidatableResponse>> UpdateIdentity([FromBody] UpdateIdentityCommand command)
    {
        var response = await _mediator.Send(command);
        return Ok(response);
    }

    [HttpDelete("{id}", Name = "DeleteIdentity")]
    public async Task<ActionResult<ValidatableResponse>> DeleteIdentity(int id)
    {
        var command = new DeleteIdentityCommand {Id = id};
        var response = await _mediator.Send(command);

        if (response.Valid)
            return Ok(response);
        return NotFound(response);
    }
}