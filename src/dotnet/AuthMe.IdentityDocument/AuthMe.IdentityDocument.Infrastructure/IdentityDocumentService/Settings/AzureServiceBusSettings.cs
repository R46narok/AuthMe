namespace AuthMe.IdentityDocumentMsrv.Infrastructure.IdentityDocumentService.Settings;

public class AzureServiceBusSettings
{
    public string Endpoint { get; set; }
    public string ValidityQueue { get; set; }
    public string ValidityOcrQueue { get; set; }
}