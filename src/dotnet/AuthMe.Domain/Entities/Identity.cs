using System.Data.SqlTypes;
using System.Diagnostics.CodeAnalysis;

namespace AuthMe.Domain.Entities;

// TODO: Maybe extend

public class IdentityProperty<T>
{
    public T? Value { get; set; }
    public bool Validated { get; set; }
    public DateTime? LastUpdated { get; set; }
}

/// <summary>
/// Represents a digital identity of a human.
/// </summary>
public class Identity
{
    public int Id { get; set; }
    
    /// <summary>
    /// Id of the associated record in the Spring service.
    /// </summary>
    public int ExternalId { get; set; }
    
    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    
    public IdentityProperty<DateTime> DateOfBirth { get; set; }
}