using System.Net.Http.Headers;
using AuthMe.IdentityDocumentMsrv.Infrastructure.Data;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.ImageService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.OcrService;
using AuthMe.IdentityDocumentMsrv.Infrastructure.ServiceBus;
using AuthMe.IdentityDocumentService.Application;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using Microsoft.EntityFrameworkCore;
using IdentityDocumentService = AuthMe.IdentityDocumentService.Web.Services.IdentityDocumentService;

var builder = WebApplication.CreateBuilder(args);

// Additional configuration is required to successfully run gRPC on macOS.
// For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682

// Add services to the container.
builder.Services.AddGrpc(options =>
{
    options.EnableDetailedErrors = true;
    options.MaxReceiveMessageSize = 10 * 1024 * 1024; // 10 MB
    options.MaxSendMessageSize = 10 * 1024 * 1024; // 10 MB
});
builder.Services.AddApplication();

builder.Services.AddTransient<IIdentityDocumentService, AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.IdentityDocumentService>();
builder.Services.AddTransient<IOcrService, OcrService>();
builder.Services.AddTransient<IImageService, ImageService>();
builder.Services.AddTransient<IIdentityDocumentValidityService,IdentityDocumentValidityService>();

builder.Services.AddHostedService<IdentityDocumentProcessor>();
builder.Services.AddSingleton<IServiceBus, AzureServiceBus>();
var connString = builder.Configuration.GetConnectionString("MsSQLDb");
builder.Services.AddDbContext<IIdentityDocumentDbContext, IdentityDocumentDbContext>(options => options.UseSqlServer(connString));

builder.Services.AddHttpClient("AzureCognitivePrediction", client =>
{
    client.BaseAddress = new Uri(builder.Configuration["AzureCognitivePredictionEndpoint"]);
    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/octet-stream"));
    client.DefaultRequestHeaders.Add("Prediction-Key", builder.Configuration["AzureCognitivePredictionKey"]);
});
builder.Services.AddHttpClient("AzureCognitiveAnalyzer", client =>
{
    client.BaseAddress = new Uri(builder.Configuration["AzureCognitiveAnalyzerEndpoint"]);
    client.DefaultRequestHeaders.Add("Ocp-Apim-Subscription-Key", builder.Configuration["AzureCognitiveAnalyzerKey"]);
});
builder.Services.AddHttpClient("AzureCognitiveAnalyzeResults", client =>
{
    client.BaseAddress = new Uri(builder.Configuration["AzureCognitiveAnalyzeResultsEndpoint"]);
    client.DefaultRequestHeaders.Add("Ocp-Apim-Subscription-Key", builder.Configuration["AzureCognitiveAnalyzerKey"]);
});
builder.Services.AddHttpClient("MinistryOfInterior", client =>
{
    client.BaseAddress = new Uri(builder.Configuration["MinistryOfInteriorEndpoint"]);
});

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    scope.ServiceProvider.GetService<IIdentityDocumentValidityService>();
    
    var services = scope.ServiceProvider;
    var context = services.GetRequiredService<IIdentityDocumentDbContext>();
    context.Database.EnsureCreated();
}

// Configure the HTTP request pipeline.
app.MapGrpcService<IdentityDocumentService>();

app.Run();