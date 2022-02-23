﻿using AuthMe.IdentityDocumentService.Application.IdentityDocuments.Queries.GetIdentityDocument;
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

    [HttpGet("{identityId}")]
    public async Task<IActionResult> GetIdentityDocument(int identityId)
    {
        var query = new GetIdentityDocumentQuery {IdentityId = identityId};
        var response = await _mediator.Send(query);

        if (response.Valid)
            return Ok(response);

        return NotFound(response);
    }
}