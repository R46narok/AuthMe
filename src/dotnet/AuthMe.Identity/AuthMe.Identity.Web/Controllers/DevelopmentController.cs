using AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.Identity.Web.Controllers;

[ApiController]
[Route("/api/dev")]
public class DevelopmentController : ControllerBase
{
    private readonly IMediator _mediator;

    public DevelopmentController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpPost]
    public async Task<IActionResult> Update([FromBody] UpdateIdentityCommand command)
    {
        var response = await _mediator.Send(command);
        
        return Ok(response);
    }
}