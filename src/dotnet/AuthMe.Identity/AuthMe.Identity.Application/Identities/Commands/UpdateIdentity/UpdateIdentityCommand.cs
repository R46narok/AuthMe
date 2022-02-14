using System.Reflection;
using AuthMe.Domain.Common;
using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.Domain.ValueObjects;
using AuthMe.IdentityService.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.Application.Identities.Commands.UpdateIdentity;

public class UpdateIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int ExternalId { get; set; }
    
    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    public string DateOfBirth { get; set; }
}

public class UpdateIdentityCommandHandler : IRequestHandler<UpdateIdentityCommand, ValidatableResponse>
{
    private readonly IIdentityDbContext _dbContext;
    private readonly IMapper _mapper;

    public UpdateIdentityCommandHandler(IIdentityDbContext dbContext, IMapper mapper)
    {
        _dbContext = dbContext;
        _mapper = mapper;
    }
    
    public async Task<ValidatableResponse> Handle(UpdateIdentityCommand request, CancellationToken cancellationToken)
    {
        var entry = _dbContext.Identities.FirstOrDefault(x => x.ExternalId == request.ExternalId);
        if (entry == null)
            return new ValidatableResponse(new[] { $"Identity with external id {request.ExternalId} could not be found." });

        if (!string.IsNullOrWhiteSpace(request.Name))
            entry.Name = new IdentityProperty<string>
                {Value = request.Name, IsValidated = false, LastUpdated = DateTime.Now};
    
        if (!string.IsNullOrWhiteSpace(request.MiddleName))
            entry.MiddleName = new IdentityProperty<string>
                {Value = request.MiddleName, IsValidated = false, LastUpdated = DateTime.Now};
        
        if (!string.IsNullOrWhiteSpace(request.Surname))
            entry.Surname = new IdentityProperty<string>
                {Value = request.Surname, IsValidated = false, LastUpdated = DateTime.Now};

        _dbContext.Identities.Update(entry);
        await _dbContext.SaveChangesAsync(cancellationToken);
        
        return new ValidatableResponse();
    }
}