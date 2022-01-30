namespace AuthMe.Domain.ValueObjects;

public class DateOfBirth : ValueObject
{
    private DateTime Date { get;  set; }
    
    public DateOfBirth()
    {
    }
    
    public DateOfBirth(DateTime dateOfBirth)
    {
        dateOfBirth = Date;
    }
    
    protected override IEnumerable<object> GetEqualityComponents()
    {
        yield return Date;
    }
}