using System.Diagnostics;
using System.Web.Http.Controllers;
using System.Web.Http.Filters;
using Microsoft.AspNetCore.Mvc.Filters;
using ActionFilterAttribute = Microsoft.AspNetCore.Mvc.Filters.ActionFilterAttribute;

namespace AuthMe.Identity.Web.Filters;

public class LogAttribute : ActionFilterAttribute
{
    private readonly string _message;
    
    public LogAttribute(string message)
    {
        _message = message;
    }

    public override Task OnActionExecutionAsync(ActionExecutingContext context, ActionExecutionDelegate next)
    {
        Trace.WriteLine(_message);
        return next();
    }
}