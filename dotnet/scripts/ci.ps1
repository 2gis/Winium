Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
#------------------------------

Import-Module '.\setup.ps1' -Args (,('msbuild'))

# Build
Invoke-MSBuild $solution $msbuildProperties -Verbose
