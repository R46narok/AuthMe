using AuthMe.Domain.Common;

namespace AuthMe.Domain.Events;

public class ValidateIdentityEvent : Event<int>
{
    public ValidateIdentityEvent(int id) : base()
    {
        Type = nameof(ValidateIdentityEvent);
        Model = id;
    }
}