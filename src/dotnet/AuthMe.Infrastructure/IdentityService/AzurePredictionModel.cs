namespace AuthMe.Infrastructure.IdentityService;

public class BoundingBox
{
    public double Left { get; set; }
    public double Top { get; set; }
    public double Width { get; set; }
    public double Height { get; set; }
}

public class AzurePrediction
{
    public double Probability { get; set; }
    public string TagId { get; set; }
    public string TagName { get; set; }
    public BoundingBox BoundingBox { get; set; }
}

public class AzurePredictionModel
{
    public string Id { get; set; }
    public string Project { get; set; }
    public string Iteration { get; set; }
    public DateTime Created { get; set; }
    public AzurePrediction[] Predictions { get; set; }
}