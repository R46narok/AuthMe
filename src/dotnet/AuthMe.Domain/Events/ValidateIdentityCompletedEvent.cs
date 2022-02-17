using AuthMe.Domain.Common;
using AuthMe.Domain.Entities;

namespace AuthMe.Domain.Events;

public class ValidateIdentityCompletedModel
{
    public int Id { get; set; }
    public IdentityProperty<string> Name { get; set; }
    public IdentityProperty<string> MiddleName { get; set; }
    public IdentityProperty<string> Surname { get; set; }
    public IdentityProperty<DateTime> DateOfBirth { get; set; }
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