using System.Net.Http.Headers;
using AuthMe.IdentityService.Application;
using AuthMe.IdentityService.Application.Common.Interfaces;
using AuthMe.IdentityService.Infrastructure.Data;
using AuthMe.IdentityService.Infrastructure.IdentityService;
using AuthMe.IdentityService.Infrastructure.IdentityValidityBus;
using AuthMe.Infrastructure.IdentityService;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// TODO: Fix when adding dep inj
builder.Services.AddApplication();

builder.Services.AddTransient<IIdentityService, IdentityService>();
builder.Services.AddSingleton<IIdentityValidityBus, IdentityValidityBus>(
    bus => new IdentityValidityBus("Endpoint=sb://authme.servicebus.windows.net/;SharedAccessKeyName=ReadWrite;SharedAccessKey=wPQ2wcxfCrVGdgMAxi6PrB6yaI6K/zSVf/53QxuYKac=;EntityPath=identity_validity", "identity_validity"));

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

var connString = builder.Configuration.GetConnectionString("MsSQLDb");
builder.Services.AddDbContext<IIdentityDbContext, IdentityDbContext>(options => options.UseSqlServer(connString));
var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var services = scope.ServiceProvider;
    var context = services.GetRequiredService<IIdentityDbContext>();
    context.Database.EnsureCreated();
}

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();