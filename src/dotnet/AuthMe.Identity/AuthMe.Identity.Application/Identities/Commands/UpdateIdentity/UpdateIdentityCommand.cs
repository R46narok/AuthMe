using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;

public class UpdateIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }
    
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
        var entry = _dbContext.Identities.FirstOrDefault(x => x.Id == request.Id);
        if (entry == null)
            return new ValidatableResponse(new[] { $"Identity with id {request.Id} could not be found." });

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