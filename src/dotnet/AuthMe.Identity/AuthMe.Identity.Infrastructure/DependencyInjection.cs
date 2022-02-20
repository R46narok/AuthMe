using System.Net.Http.Headers;
using System.Reflection;
using AuthMe.Domain.Entities;
using AuthMe.IdentityMsrv.Application.Common.Behaviors;
using AuthMe.IdentityMsrv.Application.Common.Interfaces;
using AuthMe.IdentityMsrv.Infrastructure.Data;
using AuthMe.IdentityMsrv.Infrastructure.Settings;
using FluentValidation;
using MediatR;
using Microsoft.AspNetCore.Builder;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace AuthMe.IdentityMsrv.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration configuration)
    {
        services.Configure<IdentityServiceSettings>(configuration.GetSection("IdentityService"));
        services.Configure<IdentityServiceProcessorSettings>(configuration.GetSection("AzureServiceBus"));
        
        services.AddTransient<IIdentityRepository, IdentityRepository>();
        services.AddTransient<IIdentityService, IdentityService>();
        
        services.AddHostedService<IdentityServiceProcessor>();
        
        var connString = configuration.GetConnectionString("MsSQLDb");
        services.AddDbContext<IIdentityDbContext, IdentityDbContext>(options => options.UseSqlServer(connString));
        
        return services;
    }
}