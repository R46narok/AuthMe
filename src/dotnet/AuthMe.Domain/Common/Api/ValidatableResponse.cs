namespace AuthMe.Domain.Common.Api;

/// <summary>
/// An empty response model used in the business layer.
/// </summary>
public class ValidatableResponse
{
    public List<string>? Errors { get; init; }

    public ValidatableResponse(IEnumerable<string>? errors = null)
    {
        Errors = errors?.ToList();
    }

    public bool Valid => Errors == null || !Errors.Any();
}

/// <see cref="ValidatableResponse"/>
/// <typeparam name="TModel">Type of result.</typeparam>
public class ValidatableResponse<TModel> : ValidatableResponse
{
    public TModel Result { get; init; }
    
    public ValidatableResponse(TModel model, IEnumerable<string>? errors = null) 
        : base(errors)
    {
        Result = model;
    }
}