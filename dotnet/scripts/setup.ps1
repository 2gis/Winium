Param(
    [string[]]$modules
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
#------------------------------

$configuration = 'Release'
$solutionDir = Join-Path $PSScriptRoot "../src"
$solution = Join-Path $solutionDir 'Winium.sln'
$project = Join-Path $solutionDir 'OpenQA.Selenium.Winium\OpenQA.Selenium.Winium.csproj'
$releaseDir = Join-Path $PSScriptRoot '../Release'
$assemblyInfoPath = Join-Path $solutionDir 'OpenQA.Selenium.Winium\Properties\AssemblyInfo.cs'
$changelogPath = Join-Path $PSScriptRoot '..\CHANGELOG.md'
$nuspecPath = Join-Path $solutionDir 'OpenQA.Selenium.Winium\OpenQA.Selenium.Winium.nuspec'

$msbuildProperties = @{
    'Configuration' = $configuration
}

$modulesUrl = 'https://raw.githubusercontent.com/skyline-gleb/dev-help/v0.2.1/psm'

if (!(Get-Module -ListAvailable -Name PsGet))
{
    (new-object Net.WebClient).DownloadString("http://psget.net/GetPsGet.ps1") | iex
}

foreach ($module in $modules)
{
    Install-Module -ModuleUrl "$modulesUrl/$module.psm1" -Update
}
