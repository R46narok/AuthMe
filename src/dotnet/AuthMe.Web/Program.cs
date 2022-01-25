using System.Net.Http.Headers;
using System.Reflection;
using AuthMe.Application.Common.Behaviors;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Infrastructure.Data;
using AuthMe.Infrastructure.IdentityService;
using FluentValidation;
using MediatR;
using Microsoft.AspNetCore.HttpLogging;
using Microsoft.EntityFrameworkCore;
using Microsoft.Net.Http.Headers;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// TODO: Fix when adding dep inj
var asm = typeof(CreateIdentityCommand).Assembly;
builder.Services.AddMediatR(asm);
builder.Services.AddTransient(typeof(IPipelineBehavior<,>), typeof(PerformanceBehavior<,>));
builder.Services.AddTransient(typeof(IPipelineBehavior<,>), typeof(ValidationBehavior<,>));
builder.Services.AddValidatorsFromAssembly(asm);
builder.Services.AddAutoMapper(asm);

builder.Services.AddTransient<IIdentityService, IdentityService>();

builder.Services.AddHttpClient("AzureCognitivePrediction", client =>
{
    client.BaseAddress = new Uri(builder.Configuration["AzureCognitivePredictionEndpoint"]);
    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/octet-stream"));
    client.DefaultRequestHeaders.Add("Prediction-Key", builder.Configuration["AzureCognitivePredictionKey"]);
});

var endpoint = builder.Configuration["AzureComputerVisionEndpoint"];
var key = builder.Configuration["AzureComputerVisionKey"];

var connString = builder.Configuration.GetConnectionString("MsSQLDb");
builder.Services.AddDbContext<IApplicationDbContext, ApplicationDbContext>(options => options.UseSqlServer(connString));
var app = builder.Build();

// ConfialidationBehaviorgure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

//app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();