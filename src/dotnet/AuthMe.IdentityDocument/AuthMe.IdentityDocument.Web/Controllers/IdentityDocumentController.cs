using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentImage;
using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocumentOcr;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.IdentityDocumentService.Web.Controllers;

[ApiController]
[Route("api/[controller]")]
public class IdentityDocumentController : ControllerBase
{
    private readonly IMediator _mediator;

    public IdentityDocumentController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpPost]
    public async Task<IActionResult> CreateIdentityDocument([FromBody] CreateIdentityDocumentCommand command)
    {
        var response = await _mediator.Send(command);
        
        if (response.Valid)
            return Ok(response);

        return BadRequest(response);
    }
    
    
    [HttpGet("/api/[controller]/ocr/{identityId}")]
    public async Task<IActionResult> GetIdentityDocumentOcr(int identityId)
    {
        var query = new GetIdentityDocumentOcrQuery {IdentityId = identityId};
        var response = await _mediator.Send(query);

        if (response.Valid)
            return Ok(response);

        return NotFound(response);
    }
    
    [HttpGet("/api/[controller]/image/{side}/{identityId}")]
    public async Task<IActionResult> GetIdentityDocumentImage(string side, int identityId)
    {
        var query = new GetIdentityDocumentImageQuery() {IdentityId = identityId};
        if (side == "front")
            query.Side = DocumentSide.Front;
        else
            query.Side = DocumentSide.Back;

        var response = await _mediator.Send(query);

        if (response.Valid)
            return Ok(response);

        return NotFound(response);
    }
}