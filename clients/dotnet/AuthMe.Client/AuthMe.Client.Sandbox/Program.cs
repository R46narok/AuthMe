// See https://aka.ms/new-console-template for more information

using System.Threading.Channels;
using AuthMe.Client.Core;
using AuthMe.Client.Models;

var client = new Client();

var readPropFromConsole = (string prompt) =>
{
    Console.Write($"Enter {prompt}: ");
    return Console.ReadLine();
};

var goldenToken = readPropFromConsole("goldenToken")!;
var issuerName = readPropFromConsole("issuerName")!;

var triggered = await client.TriggerValidationProcess(goldenToken, issuerName);



if (triggered)
{
    Console.WriteLine("\nTriggered process");
    
    var platinum = readPropFromConsole("platinum")!;
    var identity = new Identity
    {
        Name = readPropFromConsole("name")!
    };

    Console.WriteLine(await client.Validate(platinum, identity));
}
else
{
    Console.WriteLine("Something went wrong");
}
