FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["AuthMe.IdentityDocument/AuthMe.IdentityDocument.Web/AuthMe.IdentityDocument.Web.csproj", "AuthMe.IdentityDocument.Web/"]
RUN dotnet restore "AuthMe.IdentityDocument/AuthMe.IdentityDocument.Web/AuthMe.IdentityDocument.Web.csproj"
COPY . .
WORKDIR "/src/AuthMe.IdentityDocument.Web"
RUN dotnet build "AuthMe.IdentityDocument.Web.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "AuthMe.IdentityDocument.Web.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "AuthMe.IdentityDocument.Web.dll"]
