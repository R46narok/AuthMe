using System.Reflection;
using AuthMe.IdentityMsrv.Application.Common.Behaviors;
using FluentValidation;
using MediatR;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;

namespace AuthMe.IdentityMsrv.Application;

public static class DependencyInjection
{
    public static IServiceCollection AddApplication(this WebApplicationBuilder builder)
    {
        var asm = Assembly.GetExecutingAssembly();
        
        builder.Services.AddMediatR(asm);
        builder.Services.AddTransient(typeof(IPipelineBehavior<,>), typeof(PerformanceBehavior<,>));
        builder.Services.AddTransient(typeof(IPipelineBehavior<,>), typeof(ValidationBehavior<,>));
        builder.Services.AddValidatorsFromAssembly(asm);
        builder.Services.AddAutoMapper(asm);
        
        return builder.Services;
    }
}