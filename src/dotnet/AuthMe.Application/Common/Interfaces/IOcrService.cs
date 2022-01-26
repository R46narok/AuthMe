namespace AuthMe.Application.Common.Interfaces;

public interface IOcrService
{
    public Task<string> ReadTextFromImage(byte[] image);
}