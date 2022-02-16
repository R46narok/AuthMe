using System.Text.RegularExpressions;
using AuthMe.Domain.Common;

namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentValidityService.Http;

public static class MoiRequestCookies
{
    public static Optional<string> ExtractAspNetSession(IEnumerable<string> cookies)
    {
        foreach (var cookie in cookies)
        {
            var regex = new Regex(@"(?<=ASP.NET_SessionId=)(.*)(?=; path=/; HttpOnly; SameSite=Lax)");
            var match = regex.Match(cookie);
            if (match.Success)
                return match.Groups[1].ToString();
        }

        return new Optional<string>();
    }

    public static Optional<string> ExtractRequestVerificationToken(IEnumerable<string> cookies)
    {
        foreach (var cookie in cookies)
        {
            var regex = new Regex(@"(?<=__RequestVerificationToken=)(.*)(?=; path=/; HttpOnly)");
            var match = regex.Match(cookie);
            if (match.Success)
                return match.Groups[1].ToString();
        }

        return new Optional<string>();
    }
}