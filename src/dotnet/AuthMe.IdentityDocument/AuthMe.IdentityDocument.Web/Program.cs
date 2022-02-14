using AuthMe.IdentityDocumentService.Application;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;
using AuthMe.IdentityDocumentService.Infrastructure.Data;
using AuthMe.IdentityDocumentService.Web.Services;
using AuthMe.Infrastructure.IdentityValidityService;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Additional configuration is required to successfully run gRPC on macOS.
// For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682

// Add services to the container.
builder.Services.AddGrpc();
builder.Services.AddApplication();

builder.Services.AddHostedService(_ =>
    new IdentityValidityService("Endpoint=sb://authme.servicebus.windows.net/;SharedAccessKeyName=ReadWrite;SharedAccessKey=wPQ2wcxfCrVGdgMAxi6PrB6yaI6K/zSVf/53QxuYKac=;EntityPath=identity_validity", "identity_validity"));

var connString = builder.Configuration.GetConnectionString("MsSQLDb");
builder.Services.AddDbContext<IIdentityDocumentDbContext, IdentityDocumentDbContext>(options => options.UseSqlServer(connString));
var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    scope.ServiceProvider.GetService<IIdentityValidityService>();
    
    var services = scope.ServiceProvider;
    var context = services.GetRequiredService<IIdentityDocumentDbContext>();
    context.Database.EnsureCreated();
}

// Configure the HTTP request pipeline.
app.MapGrpcService<IdentityDocumentService>();

app.Run();