using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.Domain.Events;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.CreateIdentityDocument;

public class CreateIdentityDocumentCommand : IRequest<ValidatableResponse<int>>
{
    public int IdentityId { get; set; }
    /// <summary>
    /// In-memory content of an identity document image.
    /// PNG or JPEG
    /// </summary>
    public byte[]? DocumentFront { get; set; }

    public byte[]? DocumentBack { get; set; }
}

public class CreateIdentityCommandHandler : IRequestHandler<CreateIdentityDocumentCommand, ValidatableResponse<int>>
{
    private readonly IIdentityDocumentDbContext _dbContext;
    private readonly IMapper _mapper;
    private readonly IServiceBus _bus;

    public CreateIdentityCommandHandler(IIdentityDocumentDbContext dbContext, IMapper mapper, IServiceBus bus)
    {
        _dbContext = dbContext;
        _mapper = mapper;
        _bus = bus;
    }
    
    /// <summary>
    /// Adds an identity document to the database.
    /// </summary>
    /// <param name="request">Fluent-Validated request</param>
    /// <param name="cancellationToken"></param>
    /// <returns>
    /// The id of the new entry.
    /// </returns>
    public async Task<ValidatableResponse<int>> Handle(CreateIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        if (await _dbContext.IdentityDocuments!.FirstOrDefaultAsync(
                x => x.IdentityId == request.IdentityId, cancellationToken) != null)
            return new ValidatableResponse<int>(-1, new[] {$"A document is already attached to id {request.IdentityId}"});
        
        var document = _mapper.Map<IdentityDocument>(request);
        var entry = _dbContext.IdentityDocuments!.Add(document);
        
        await _dbContext.SaveChangesAsync(cancellationToken);

        await _bus.Send(new ValidateIdentityEvent(request.IdentityId), "identity_validity");
        
        return new ValidatableResponse<int>(entry.Entity.Id);
    }
}