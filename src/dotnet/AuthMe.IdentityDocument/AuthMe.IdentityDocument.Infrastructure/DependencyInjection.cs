using System.Net.Http.Headers;
using System.Reflection;
using AuthMe.IdentityDocumentMsrv.Infrastructure.Data;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService.Settings;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrValidityService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.ServiceBus;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration configuration)
    {
        var asm = Assembly.GetExecutingAssembly();
        
        services.Configure<AzureServiceBusSettings>(configuration.GetSection("AzureServiceBus"));
        
        services.AddTransient<IIdentityDocumentService, AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.IdentityDocumentService>();
        services.AddTransient<IOcrService, OcrService.OcrService>();
        services.AddTransient<IImageService, ImageService.ImageService>();
        services.AddTransient<IIdentityDocumentValidityService,IdentityDocumentValidityService.IdentityDocumentValidityService>();
        services.AddTransient<IOcrValidityService, OcrValidityService.OcrValidityService>();
    
        services.AddHostedService<IdentityDocumentProcessor>();
        services.AddSingleton<IServiceBus, AzureServiceBus>();
        services.AddDbContext<IIdentityDocumentDbContext, IdentityDocumentDbContext>(
            options => options.UseSqlServer(configuration.GetConnectionString("MsSQLDb")));
        services.AddTransient<IIdentityDocumentRepository, IdentityDocumentRepository>();
        
        services.AddHttpClients(configuration);
        
        return services;
    }

    private static void AddHttpClients(this IServiceCollection services, IConfiguration configuration)
    {
        services.Configure<AzureCognitivePredictionSettings>(configuration.GetSection("AzureCognitivePrediction"));
        services.Configure<AzureOcrSettings>(configuration.GetSection("AzureOcr"));
        services.Configure<MinistryOfInteriorSettings>(configuration.GetSection("MinistryOfInterior"));
        
        services.AddHttpClient("AzureCognitivePrediction", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<AzureCognitivePredictionSettings>>()!.Value;
    
            client.BaseAddress = new Uri(options.Endpoint);
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/octet-stream"));
            client.DefaultRequestHeaders.Add("Prediction-Key", options.Key);
        });
        
        services.AddHttpClient("AzureOcr", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<AzureOcrSettings>>()!.Value;
            
            client.BaseAddress = new Uri(options.Endpoint);
            client.DefaultRequestHeaders.Add("Ocp-Apim-Subscription-Key", options.Key);
        });
        
        services.AddHttpClient("MinistryOfInterior", (serviceProvider, client) =>
        {
            var options = serviceProvider.GetService<IOptions<MinistryOfInteriorSettings>>()!.Value;
            client.BaseAddress = new Uri(options.Endpoint);
        });
    }
}