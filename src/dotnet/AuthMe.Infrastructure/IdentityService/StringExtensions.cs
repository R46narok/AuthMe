using System.Text;

namespace AuthMe.Infrastructure.IdentityService;

public static class StringExtensions
{
    public static string ToTitleCase(this string str)
    {
        var builder = new StringBuilder(str.ToLower());
        builder[0] = Char.ToUpper(builder[0]);
        return builder.ToString();
    }
}