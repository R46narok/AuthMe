using AuthMe.IdentityDocumentMsrv.Infrastructure;
using AuthMe.IdentityDocumentService.Application;
using AuthMe.IdentityDocumentService.Application.Common.Interfaces;

var builder = WebApplication.CreateBuilder(args);

// Additional configuration is required to successfully run gRPC on macOS.
// For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();

builder.Services.AddApplication();
builder.Services.AddInfrastructure(builder.Configuration);

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    scope.ServiceProvider.GetService<IIdentityDocumentValidityService>();
    
    var services = scope.ServiceProvider;
    var context = services.GetRequiredService<IIdentityDocumentDbContext>();
    context.Database.EnsureCreated();
}

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();