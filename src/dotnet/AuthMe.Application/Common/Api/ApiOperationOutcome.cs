namespace AuthMe.Application.Common.Api;

public class ApiOperationOutcome
{
    public ApiOperationOutcome()
    {
        Message = string.Empty;
        ErrorId = string.Empty;
        Errors = Enumerable.Empty<string>();
    }

    public OpResult OpResult { get; set; }
    public string ErrorId { get; set; }
    public string Message { get; set; }
    public bool IsError { get; set; }
    public bool IsValidationFail { get; set; }

    public IEnumerable<string> Errors { get; set; }

    public static ApiOperationOutcome SuccessfulOutcome => new ApiOperationOutcome
    {
        Errors = Enumerable.Empty<string>(),
        ErrorId = string.Empty,
        IsError = false,
        IsValidationFail = false,
        Message = string.Empty,
        OpResult = OpResult.Success
    };

    public static ApiOperationOutcome UnsuccessfulOutcome => new ApiOperationOutcome
    {
        Errors = Enumerable.Empty<string>(),
        ErrorId = string.Empty,
        IsError = true,
        IsValidationFail = false,
        Message = string.Empty,
        OpResult = OpResult.Fail
    };

    public static ApiOperationOutcome ValidationFailOutcome(IEnumerable<string> errors, string message = null) => new ApiOperationOutcome
    {
        Errors = errors ?? Enumerable.Empty<string>(),
        ErrorId = string.Empty,
        IsError = false,
        IsValidationFail = true,
        Message = message ?? string.Empty,
        OpResult = OpResult.Fail
    };

}