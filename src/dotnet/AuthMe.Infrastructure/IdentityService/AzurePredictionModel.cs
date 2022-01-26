namespace AuthMe.Infrastructure.IdentityService;

public class AzurePredictionBoundingBox<T> where T : struct
{
    public T Left { get; set; }
    public T Top { get; set; }
    public T Width { get; set; }
    public T Height { get; set; }
}

public class AzurePrediction
{
    public double Probability { get; set; }
    public string TagId { get; set; }
    public string TagName { get; set; }
    public AzurePredictionBoundingBox<double> BoundingBox { get; set; }
}

public class AzurePredictionModel
{
    public string Id { get; set; }
    public string Project { get; set; }
    public string Iteration { get; set; }
    public DateTime Created { get; set; }
    public AzurePrediction[] Predictions { get; set; }
}