using System.Text;
using System.Text.RegularExpressions;
using AuthMe.Domain.Common;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Http;

public static class MoIResponseExtensions
{
    public static Optional<string> ExtractRequestVerificationToken(this HttpResponseMessage response)
    {
        var stream = response.Content.ReadAsStream();
        var bytes = new byte[stream.Length];
        stream.Read(bytes, 0, (int) stream.Length);
        var str = Encoding.UTF8.GetString(bytes);
        
        File.WriteAllTextAsync("index2.html", str);
        
        var regex = new Regex("(?<=<input name=\"__RequestVerificationToken\" type=\"hidden\" value=\")(.*)(?=\" />)");
        var match = regex.Match(str);
        
        if (match.Success)
            return match.Groups[1].ToString();
        
        return new Optional<string>();
    }
}