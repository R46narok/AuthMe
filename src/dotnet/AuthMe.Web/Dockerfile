FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["AuthMe.Web/AuthMe.Web.csproj", "AuthMe.Web/"]
COPY ["AuthMe.Application/AuthMe.Application.csproj", "AuthMe.Application/"]
COPY ["AuthMe.Domain/AuthMe.Domain.csproj", "AuthMe.Domain/"]
COPY ["AuthMe.Infrastructure/AuthMe.Infrastructure.csproj", "AuthMe.Infrastructure/"]
RUN dotnet restore "AuthMe.Web/AuthMe.Web.csproj"
COPY . .
WORKDIR "/src/AuthMe.Web"
RUN dotnet build "AuthMe.Web.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "AuthMe.Web.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "AuthMe.Web.dll"]
