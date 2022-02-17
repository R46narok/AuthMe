using AuthMe.Domain.Common;

namespace AuthMe.Domain.Events;

public class ValidateIdentityCompletedModel
{
    public int Id { get; set; }
    public string Name { get; set; }
    public string MiddleName { get; set; }
    public string Surname { get; set; }
    public string DateOfBirth { get; set; }
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