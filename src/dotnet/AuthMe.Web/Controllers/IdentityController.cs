using AuthMe.Application.Common.Api;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Queries.GetIdentity;
using AuthMe.Infrastructure.Data;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace AuthMe.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityController : ControllerBase
{
    private readonly ILogger<IdentityController> _logger;
    private readonly IIdentityService _identityService;
    private readonly IMediator _mediator;

    public IdentityController(ApplicationDbContext context, ILogger<IdentityController> logger, IMediator mediator, IIdentityService identityService)
    {
        _logger = logger;
        _identityService = identityService;
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
    public async Task<ActionResult<ValidatableResponse<int>>> CreateIdentity([FromForm] int externalId, [FromForm] IFormFile file)
    {
        var stream = file.OpenReadStream();
        var bytes = new byte[stream.Length];
        var length = (int) stream.Length;
        
        await stream.ReadAsync(bytes, 0, length);

        await _identityService.CreateIdentity(0, bytes);
        
        return BadRequest();
    }
}