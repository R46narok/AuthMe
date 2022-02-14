using AuthMe.Application.Identities.Commands.ValidateIdentity;
using AuthMe.Domain.Common;
using AuthMe.Domain.Events;
using AuthMe.IdentityService.Application.Common.Interfaces;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.Identity.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityValidityController : ControllerBase
{
    private readonly IMediator _mediator;
    private readonly IIdentityValidityBus _bus;

    public IdentityValidityController(IMediator mediator, IIdentityValidityBus bus)
    {
        _mediator = mediator;
        _bus = bus;
    }

    [HttpPost(template: "{id:int}")]
    public async Task<ActionResult> TriggerIdentityValidation(int id)
    {
        var command = new ValidateIdentityCommand
        {
            IdentityId = id
        };
        
        var response = await _mediator.Send(command);

        if (response.IsValid)
            return Ok();
        return BadRequest(response.Errors);
    }
}