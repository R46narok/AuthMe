
using System.Net;
using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Common.Models;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Application.Identities.Queries.GetIdentity;
using AuthMe.Application.IdentityDocuments.Commands.CreateIdentityDocument;
using AuthMe.Infrastructure.Data;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Net.Http.Headers;
using MediaTypeHeaderValue = System.Net.Http.Headers.MediaTypeHeaderValue;

namespace AuthMe.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityController : ControllerBase
{
    private readonly ILogger<IdentityController> _logger;
    private readonly IMediator _mediator;
    private readonly IComputerVision _computerVision;
    private readonly IIdentityDocumentReader _documentReader;
    
    public IdentityController(ApplicationDbContext context, ILogger<IdentityController> logger, IMediator mediator, IComputerVision computerVision, IIdentityDocumentReader documentReader)
    {
        _logger = logger;
        _mediator = mediator;
        _computerVision = computerVision;
        _documentReader = documentReader;
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
    public async Task<ActionResult<ValidatableResponse<int>>> CreateIdentity([FromForm] int externalId, [FromForm] IFormFile file)
    {
        var stream = file.OpenReadStream();
        var bytes = new byte[stream.Length];
        var length = (int) stream.Length;
        
        await stream.ReadAsync(bytes, 0, length);
        
        var createDocumentCmd = new CreateIdentityDocumentCommand
        {
            Image = bytes,
            Length = length
        };

        var result = await _mediator.Send(createDocumentCmd);
        if (!result.IsValid) return BadRequest(result);
        
        var documentId = result.Result;
        var createIdentityCmd = new CreateIdentityCommand
        {
            ExternalId = externalId,
            DocumentId = documentId
        };
        return await _mediator.Send(createDocumentCmd);

    }
}