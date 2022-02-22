using AuthMe.Domain.Common;
using AuthMe.Domain.Entities;

namespace AuthMe.Domain.Events;

public class ValidateIdentityCompletedModel
{
    public int Id { get; set; }
    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    public DateTime? DateOfBirth { get; set; }
}

public class ValidateIdentityCompletedEvent : Event<ValidateIdentityCompletedModel>
{
    public ValidateIdentityCompletedEvent()
    {
        
    }
    
    public ValidateIdentityCompletedEvent(ValidateIdentityCompletedModel id)
    {
        Type = nameof(ValidateIdentityCompletedEvent);
        Model = id;
    }
}