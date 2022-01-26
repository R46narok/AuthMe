using AuthMe.Application.Common.Interfaces;
using AuthMe.Infrastructure.Data;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Routing.Template;

namespace AuthMe.Web.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class IdentityDocumentController : ControllerBase
{
    private readonly IApplicationDbContext _dbContext;
    private readonly ILogger<IdentityDocumentController> _logger;
    private readonly IMediator _mediator;

    public IdentityDocumentController(IApplicationDbContext context, ILogger<IdentityDocumentController> logger, IMediator mediator)
    {
        _dbContext = context;
        _logger = logger;
        _mediator = mediator;
    }

    [HttpGet("{name}", Name = "GetIdentityDocument")]
    public ActionResult GetIdentityDocument(int id)
    {
        var entry = _dbContext.IdentityDocuments.FirstOrDefault(x => x.Id == id);
        if (entry != null)
        {
            return File(entry.Image, "image/png"); // TODO:
        }

        return NotFound();
    }
}