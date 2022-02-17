using AuthMe.Domain.Common.Api;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;

namespace AuthMe.IdentityMsrv.Application.Identities.Commands.UpdateIdentity;

public class UpdateIdentityCommand : IRequest<ValidatableResponse>, IValidatable
{
    public int Id { get; set; }
    
    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    public IdentityProperty<DateTime> DateOfBirth { get; set; }
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

        entry.Name = request.Name;
        entry.MiddleName = request.MiddleName;
        entry.Surname = request.Surname;
        entry.DateOfBirth = request.DateOfBirth;
        
        _dbContext.Identities.Update(entry);
        await _dbContext.SaveChangesAsync(cancellationToken);
        
        return new ValidatableResponse();
    }
}