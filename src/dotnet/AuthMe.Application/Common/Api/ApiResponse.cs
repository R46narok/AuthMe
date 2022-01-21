namespace AuthMe.Application.Common.Api;

public class ApiResponse<T> : ApiResponse
{
    public T Data { get; set; }
}

public class ApiResponse
{
    public ApiResponse()
    {
    }
    public ApiOperationOutcome Outcome { get; set; }
}