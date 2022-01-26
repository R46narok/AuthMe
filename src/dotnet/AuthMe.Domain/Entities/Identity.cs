using System.Data.SqlTypes;

namespace AuthMe.Domain.Entities;

// TODO: Maybe extend

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
    
    public string FirstName { get; set; }
    public string MiddleName { get; set; }
    public string LastName { get; set; }
    
    public DateTime DateOfBirth { get; set; }
}