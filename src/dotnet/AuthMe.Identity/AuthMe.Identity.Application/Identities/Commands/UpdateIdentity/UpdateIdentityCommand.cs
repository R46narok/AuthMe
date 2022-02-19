using System.Diagnostics.CodeAnalysis;
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

[SuppressMessage("ReSharper", "UnusedType.Global")]
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
        var identity = _mapper.Map<Identity>(request);
        
        _dbContext.Identities.Update(identity);
        await _dbContext.SaveChangesAsync(cancellationToken);
        
        return new ValidatableResponse();
    }
}