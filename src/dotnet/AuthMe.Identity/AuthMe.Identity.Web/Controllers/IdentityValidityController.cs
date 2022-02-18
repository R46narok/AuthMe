using AuthMe.Identity.Web.Extensions;
using AuthMe.IdentityMsrv.Application.Identities.Commands.AttachIdentityDocument;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.Identity.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityValidityController : ControllerBase
{
    private readonly IMediator _mediator;

    public IdentityValidityController(IMediator mediator)
    {
        _mediator = mediator;

    }

    [HttpPost(Name = "TriggerIdentityValidation")]
    public async Task<ActionResult> TriggerIdentityValidation(
        [FromForm] int id, [FromForm] IFormFile documentFront, [FromForm] IFormFile documentBack)
    {
        var command = new AttachIdentityDocumentCommand()
        {
            IdentityId = id,
            DocumentFront = await documentFront.ReadAsBytesAsync(),
            DocumentBack = await documentBack.ReadAsBytesAsync()
        };
        
        var response = await _mediator.Send(command);

        if (response.Valid)
            return Ok();
        
        return BadRequest(response.Errors);
    }
}