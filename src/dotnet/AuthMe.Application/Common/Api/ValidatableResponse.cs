namespace AuthMe.Application.Common.Api;

public class ValidatableResponse
{
    public List<string>? Errors { get; init; }

    public ValidatableResponse(IEnumerable<string>? errors)
    {
        Errors = errors?.ToList();
    }

    public bool IsValid => Errors == null || !Errors.Any();
}

public class ValidatableResponse<TModel> : ValidatableResponse
    where TModel : class
{
    public TModel Result { get; init; }
    
    public ValidatableResponse(TModel model, IEnumerable<string>? errors = null) 
        : base(errors)
    {
        Result = model;
    }
}