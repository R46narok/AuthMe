using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace AuthMe.IdentityService.Infrastructure.Data;

/// <summary>
/// Converts DateOnly to DateTime and vice versa.
/// </summary>
public class DateOnlyConverter : ValueConverter<DateOnly, DateTime>
{
    /// <summary>
    /// Creates a new instance of this converter.
    /// </summary>
    public DateOnlyConverter() : base(
        d => d.ToDateTime(TimeOnly.MinValue),
        d => DateOnly.FromDateTime(d))
    { }
}