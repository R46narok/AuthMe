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
    public static IServiceCollection AddInfrastructure(this WebApplicationBuilder builder)
    {
        builder.Services.Configure<IdentityServiceSettings>(builder.Configuration.GetSection("IdentityService"));
        builder.Services.Configure<IdentityServiceProcessorSettings>(builder.Configuration.GetSection("AzureServiceBus"));
        
        builder.Services.AddTransient<IIdentityRepository, IdentityRepository>();
        builder.Services.AddTransient<IIdentityService, IdentityService>();
        
        builder.Services.AddHostedService<IdentityServiceProcessor>();
        
        var connString = builder.Configuration.GetConnectionString("MsSQLDb");
        builder.Services.AddDbContext<IIdentityDbContext, IdentityDbContext>(options => options.UseSqlServer(connString));
        
        return builder.Services;
    }
}