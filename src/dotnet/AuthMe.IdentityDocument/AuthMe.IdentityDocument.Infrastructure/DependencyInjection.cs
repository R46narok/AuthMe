using System.Net.Http.Headers;
using System.Reflection;
using AuthMe.IdentityDocumentMsrv.Infrastructure.Data;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.ServiceBus;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.AspNetCore.Builder;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructure(this WebApplicationBuilder builder)
    {
        var asm = Assembly.GetExecutingAssembly();
        
        builder.Services.Configure<AzureServiceBusSettings>(builder.Configuration.GetSection("AzureServiceBus"));
        
        builder.Services.AddTransient<IIdentityDocumentService, AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.IdentityDocumentService>();
        builder.Services.AddTransient<IOcrService, OcrService.OcrService>();
        builder.Services.AddTransient<IImageService, ImageService.ImageService>();
        builder.Services.AddTransient<IIdentityDocumentValidityService,IdentityDocumentValidityService.IdentityDocumentValidityService>();

        builder.Services.AddHostedService<IdentityDocumentProcessor>();
        builder.Services.AddSingleton<IServiceBus, AzureServiceBus>();
        builder.Services.AddDbContext<IIdentityDocumentDbContext, IdentityDocumentDbContext>(
            options => options.UseSqlServer(builder.Configuration.GetConnectionString("MsSQLDb")));
        builder.Services.AddTransient<IIdentityDocumentRepository, IdentityDocumentRepository>();
        
        builder.AddHttpClients();
        
        return builder.Services;
    }

    private static void AddHttpClients(this WebApplicationBuilder builder)
    {
        builder.Services.Configure<AzureCognitivePredictionSettings>(builder.Configuration.GetSection("AzureCognitivePrediction"));
        builder.Services.Configure<AzureOcrSettings>(builder.Configuration.GetSection("AzureOcr"));
        builder.Services.Configure<MinistryOfInteriorSettings>(builder.Configuration.GetSection("MinistryOfInterior"));
        
        builder.Services.AddHttpClient("AzureCognitivePrediction", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<AzureCognitivePredictionSettings>>()!.Value;
    
            client.BaseAddress = new Uri(options.Endpoint);
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/octet-stream"));
            client.DefaultRequestHeaders.Add("Prediction-Key", options.Key);
        });
        
        builder.Services.AddHttpClient("AzureOcr", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<AzureOcrSettings>>()!.Value;
            
            client.BaseAddress = new Uri(options.Endpoint);
            client.DefaultRequestHeaders.Add("Ocp-Apim-Subscription-Key", options.Key);
        });
        
        builder.Services.AddHttpClient("MinistryOfInterior", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<MinistryOfInteriorSettings>>()!.Value;
            client.BaseAddress = new Uri(options.Endpoint);
        });
    }
}