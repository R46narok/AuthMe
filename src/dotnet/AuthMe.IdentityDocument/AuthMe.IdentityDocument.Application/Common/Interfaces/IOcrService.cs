namespace AuthMe.IdentityDocumentService.Application.Common.Interfaces;

/// <summary>
/// OCR (Optical-Character-Recognition)
/// </summary>
public interface IOcrService
{
    /// <summary>
    /// Converts human-readable images to machine-readable text.
    /// TODO: Return optional
    /// </summary>
    /// <param name="image">An image to read from.</param>
    /// <returns>string.Empty if nothing is found. </returns>
    public Task<string> ReadTextFromImage(byte[] image);
}