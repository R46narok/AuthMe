using AuthMe.Domain.Common;

namespace AuthMe.Domain.Events;

public class ValidateOcrEvent : Event<int>
{
    public ValidateOcrEvent()
    {
        
    }
    
    public ValidateOcrEvent(int identityId)
    {
        Model = identityId;
        Type = nameof(ValidateOcrEvent);
    }
}