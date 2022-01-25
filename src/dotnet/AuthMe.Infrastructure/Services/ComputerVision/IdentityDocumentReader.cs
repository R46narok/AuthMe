using System.Globalization;
using System.Reflection;
using System.Text.RegularExpressions;
using AuthMe.Application.Common.Interfaces;
using AuthMe.Application.Common.Models;
using AuthMe.Domain.Common;

namespace AuthMe.Infrastructure.Services.ComputerVision;

internal class IdentityDocumentRaw
{
    public string Name { get; set; }
    public string FathersName { get; set; }
    public string Surname { get; set; }
    public string DateOfBirth { get; set; }
}

public class IdentityDocumentReader : IIdentityDocumentReader
{
    private readonly IComputerVision _computerVision;
    private readonly PropertyInfo[] _documentProperties;
    private readonly Dictionary<PropertyInfo, string> _mappedDocumentProperties;

    public IdentityDocumentReader(IComputerVision computerVision)
    {
        _computerVision = computerVision;
        _documentProperties = typeof(IdentityDocumentRaw).GetProperties();
        _mappedDocumentProperties = new Dictionary<PropertyInfo, string>();

        var getProperty = (string name) => _documentProperties.FirstOrDefault(x => x.Name == name);
        _mappedDocumentProperties.Add(getProperty(nameof(IdentityDocumentRaw.Name)), "Name");
        _mappedDocumentProperties.Add(getProperty(nameof(IdentityDocumentRaw.FathersName)), "Father's name");
        _mappedDocumentProperties.Add(getProperty(nameof(IdentityDocumentRaw.Surname)), "Surname");
        _mappedDocumentProperties.Add(getProperty(nameof(IdentityDocumentRaw.DateOfBirth)), "Date of birth");
    }
    
    public async Task<IdentityDocumentModel> ReadIdentityDocumentAsync(string url)
    {
        var lines = await _computerVision.ReadFileUrl(url);

        var now = DateTime.Now;
        IdentityDocumentRaw document = new IdentityDocumentRaw();

        for (int i = 0; i < lines.Count; i++)
        {
            var property = MapToProperty(lines[i], 2);
            if (property.HasValue)
            {
                var match  = Regex.Match(lines[i], @"\d{2}\.\d{2}\.\d{4}");
                if (match.Success)
                {
                    document.DateOfBirth = match.Value;
                    continue;
                }
                
                var titleCase = lines[i + 1];
                property.Value.SetValue(document, titleCase);
            }
        }
        
        return new IdentityDocumentModel
        {
            Name = document.Name.ToTitleCase(),
            FathersName = document.FathersName.ToTitleCase(),
            Surname = document.Surname.ToTitleCase(),
            DateOfBirth = DateTime.ParseExact(document.DateOfBirth, "dd.MM.yyyy", CultureInfo.CurrentCulture)
        };
    }

    private Optional<PropertyInfo> MapToProperty(string str, int tolerance)
    {
        foreach (var property in _documentProperties)
        {
            if (str.Distance(_mappedDocumentProperties[property]) <= tolerance)
                return new Optional<PropertyInfo>(property);
            
            const string datePattern = @"\/([\s\S]*)$";
            var match = Regex.Match(str, datePattern);
            if (match.Groups.Count > 0)
            {
                var trimmed = match.Groups[1].ToString();
                const string dateOfBirth = "Date of birth";
                if (trimmed.Length > dateOfBirth.Length &&
                    trimmed.Substring(0, dateOfBirth.Length).Distance(dateOfBirth) <= 1)
                    return new Optional<PropertyInfo>(property);
            }
        }

        return new Optional<PropertyInfo>();
    }
}