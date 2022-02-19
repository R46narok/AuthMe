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
    private readonly IMapper _mapper;
    private readonly IServiceBus _bus;
    private readonly IIdentityDocumentRepository _repository;

    public CreateIdentityCommandHandler(IIdentityDocumentRepository repository, IMapper mapper, IServiceBus bus)
    {
        _repository = repository;
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
        var document = _mapper.Map<IdentityDocument>(request);
        var id = await _repository.CreateDocumentAsync(document);

        await _bus.Send(new ValidateIdentityEvent(request.IdentityId), "identity_validity");
        
        return new ValidatableResponse<int>(id);
    }
}