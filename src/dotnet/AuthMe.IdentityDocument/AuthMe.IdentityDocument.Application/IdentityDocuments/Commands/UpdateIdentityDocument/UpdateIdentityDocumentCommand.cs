using AuthMe.Domain.Common.Api;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using MediatR;

namespace AuthMe.IdentityDocumentService.Application.IdentityDocuments.Commands.UpdateIdentityDocument;

public class UpdateIdentityDocumentCommand : IRequest<ValidatableResponse>
{
    public int IdentityId { get; set; }
    public string? OcrName { get; set; }
    public string? OcrMiddleName { get; set; }
    public string? OcrSurname { get; set; }
    public string? OcrDateOfBirth { get; set; }
}

public class UpdateIdentityDocumentCommandHandler : IRequestHandler<UpdateIdentityDocumentCommand, ValidatableResponse>
{
    private readonly IIdentityDocumentRepository _repository;

    public UpdateIdentityDocumentCommandHandler(IIdentityDocumentRepository repository)
    {
        _repository = repository;
    }
    
    public async Task<ValidatableResponse> Handle(UpdateIdentityDocumentCommand request, CancellationToken cancellationToken)
    {
        var document = await _repository.GetDocument(request.IdentityId);
        
        document!.OcrName = request.OcrName;
        document.OcrMiddleName = request.OcrMiddleName;
        document.OcrSurname= request.OcrSurname;
        document.OcrDateOfBirth = request.OcrDateOfBirth;

        await _repository.UpdateDocumentAsync(document);
        return new();
    }
}