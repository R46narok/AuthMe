using System.Text;

namespace AuthMe.Infrastructure.IdentityService;

public static class StringExtensions
{
    /// <summary>
    /// Converts a string to title case.
    /// </summary>
    /// <example>
    /// var str = "IVANOV";
    /// str.ToTitleCase() -> "Ivanov"
    /// </example>
    /// <param name="str">String to convert from.</param>
    /// <returns>A title-cased string.</returns>
    public static string ToTitleCase(this string str)
    {
        var builder = new StringBuilder(str.ToLower());
        builder[0] = Char.ToUpper(builder[0]);
        return builder.ToString();
    }
}