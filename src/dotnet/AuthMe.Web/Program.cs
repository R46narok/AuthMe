using System.Reflection;
using AuthMe.Application.Common.Behaviors;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Identities.Commands.CreateIdentity;
using AuthMe.Infrastructure.Data;
using AuthMe.Infrastructure.Services.ComputerVision;
using FluentValidation;
using MediatR;
using Microsoft.AspNetCore.HttpLogging;
using Microsoft.EntityFrameworkCore;

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

var endpoint = builder.Configuration["AzureComputerVisionEndpoint"];
var key = builder.Configuration["AzureComputerVisionKey"];
builder.Services.AddTransient<IComputerVision, AzureComputerVision>(x => new AzureComputerVision(endpoint, key));
builder.Services.AddTransient<IIdentityDocumentReader, IdentityDocumentReader>();

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