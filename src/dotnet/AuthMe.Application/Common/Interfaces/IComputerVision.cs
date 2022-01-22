namespace AuthMe.Application.Common.Interfaces;

public interface IComputerVision
{
    public Task<List<string>> ReadFileUrl(string urlFile);
}