using System.Net.Http.Headers;
using AuthMe.Application;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Infrastructure.Data;
using AuthMe.Infrastructure.IdentityService;
using AuthMe.Infrastructure.ImageService;
using AuthMe.Infrastructure.OcrService;
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
builder.Services.AddTransient<IOcrService, OcrService>();
builder.Services.AddTransient<IImageService, ImageService>();

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
Console.WriteLine(connString);
builder.Services.AddDbContext<IApplicationDbContext, ApplicationDbContext>(options => options.UseSqlServer(connString));
var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var services = scope.ServiceProvider;
    var context = services.GetRequiredService<IApplicationDbContext>();
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