Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
#------------------------------

$mvnPath = ''
if (Get-Command 'mvn' -ErrorAction SilentlyContinue) 
{
    $mvnPath = 'mvn'
}
else
{
    Write-Output 'Unable to find mvn in your PATH'
    Exit 1
}

$projectDir = Join-Path $PSScriptRoot "../"

# Compile
& $mvnPath -f $projectDir compile
if ($LASTEXITCODE -ne 0)
{
    Write-Output "Compile failed. See output"
    Exit $LASTEXITCODE
}
