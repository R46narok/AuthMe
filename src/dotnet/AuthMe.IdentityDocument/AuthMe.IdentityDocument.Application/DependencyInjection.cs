using System.Reflection;
using AuthMe.IdentityDocumentService.Application.Common.Behaviours;
using FluentValidation;
using MediatR;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;

namespace AuthMe.IdentityDocumentService.Application;

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