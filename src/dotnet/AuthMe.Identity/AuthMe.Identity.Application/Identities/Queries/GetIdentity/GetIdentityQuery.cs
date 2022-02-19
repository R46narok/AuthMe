using AuthMe.Domain.Common.Api;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AutoMapper;
using MediatR;
using Microsoft.Extensions.Logging;

namespace AuthMe.IdentityMsrv.Application.Identities.Queries.GetIdentity;

public class GetIdentityQuery : IRequest<ValidatableResponse<IdentityDto>>, IValidatable
{
    public int Id { get; set; }
}

public class GetIdentityQueryHandler : IRequestHandler<GetIdentityQuery, ValidatableResponse<IdentityDto>>
{
    private readonly IMapper _mapper;
    private readonly IIdentityRepository _repository;
    private readonly ILogger<GetIdentityQueryHandler> _logger;

    public GetIdentityQueryHandler(IMapper mapper, IIdentityRepository repository, ILogger<GetIdentityQueryHandler> logger)
    {
        _mapper = mapper;
        _repository = repository;
        _logger = logger;
    }

    /// <summary>
    /// Gets an Identity from the database.
    /// </summary>
    /// <param name="request">Fluent-Validated request</param>
    /// <param name="cancellationToken"></param>
    /// <returns>
    /// An Identity, if present.
    /// Errors:
    /// - Requested Identity does not exist in the database.
    /// </returns>
    public async Task<ValidatableResponse<IdentityDto>> Handle(GetIdentityQuery request, CancellationToken cancellationToken)
    {
        var identity = await _repository.GetIdentityAsync(request.Id);
        _logger.LogInformation("Retrieved identity with id {Id}", request.Id);
        
        return new ValidatableResponse<IdentityDto>(_mapper.Map<IdentityDto>(identity));
    }
}