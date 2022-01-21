﻿using AuthMe.Application.Common.Api;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Infrastructure.Data;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace AuthMe.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityController : ControllerBase
{
    private readonly ILogger<IdentityController> _logger;
    private readonly IMediator _mediator;
    
    public IdentityController(ApplicationDbContext context, ILogger<IdentityController> logger, IMediator mediator)
    {
        _logger = logger;
        _mediator = mediator;
    }
}