using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.DeleteIdentityDocument;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocument;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.IdentityDocumentService.Web.Controllers;

[ApiController]
[Route("api/identity/document")]
public class IdentityDocumentController : ControllerBase
{
    private readonly IMediator _mediator;
    private readonly ILogger<IdentityDocumentController> _logger;

    public IdentityDocumentController(IMediator mediator, ILogger<IdentityDocumentController> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    [HttpGet("{identityId}")]
    public async Task<IActionResult> GetIdentityDocument(int identityId)
    {
        var query = new GetIdentityDocumentQuery {IdentityId = identityId};
        var response = await _mediator.Send(query);

        if (response.Valid)
            return Ok(response);

        return NotFound(response);
    }

    [HttpDelete("{identityId}")]
    public async Task<IActionResult> DeleteIdentityDocument(int identityId)
    {
        var command = new DeleteIdentityDocumentCommand {IdentityId = identityId};
        var response = await _mediator.Send(command);

        if (response.Valid)
            return Ok(response);

        return NotFound(response);
    }
}