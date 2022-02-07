function Write-ColorOutput($ForegroundColor)
{
    # save the current color
    $fc = $host.UI.RawUI.ForegroundColor

    # set the new color
    $host.UI.RawUI.ForegroundColor = $ForegroundColor

    # output
    if ($args) {
        Write-Output $args
    }
    else {
        $input | Write-Output
    }

    # restore the original color
    $host.UI.RawUI.ForegroundColor = $fc
}

$environment = $args[0]

if ($environment -eq 'dotnet')
{
    Write-ColorOutput green ("-> Selected development environment: .NET")

    Write-Output "-> Opening GitHub repo"
    Start-Process "https://github.com/r46narok/authme"

    Write-Output "-> Opening Portainer on port 9443"
    Start-Process "https://localhost:9443"

    Write-Output "-> Opening Azure Portal"
    Start-Process "https://portal.azure.com/"
}