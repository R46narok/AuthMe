namespace AuthMe.Identity.Web.Extensions;

public static class FormFileExtensions
{
    public static async Task<byte[]> ReadAsBytesAsync(this IFormFile file)
    {
        var stream = file.OpenReadStream();
        var bytes = new byte[stream.Length];
        var length = (int) stream.Length;
        
        await stream.ReadAsync(bytes, 0, length);

        return bytes;
    }
}