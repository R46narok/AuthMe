using System.Reflection;
using AuthMe.IdentityDocumentService.Application.Common.Behaviours;
using FluentValidation;
using MediatR;
using Microsoft.Extensions.DependencyInjection;

namespace AuthMe.IdentityDocumentService.Application;

public static class DependencyInjection
{
    public static IServiceCollection AddApplication(this IServiceCollection services)
    {
        var asm = Assembly.GetExecutingAssembly();
        
        services.AddMediatR(asm);
        services.AddTransient(typeof(IPipelineBehavior<,>), typeof(PerformanceBehavior<,>));
        services.AddTransient(typeof(IPipelineBehavior<,>), typeof(ValidationBehavior<,>));
        services.AddValidatorsFromAssembly(asm);
        services.AddAutoMapper(asm);
        
        return services;
    }
}