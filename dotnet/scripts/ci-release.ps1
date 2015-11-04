Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'
#------------------------------

Import-Module '.\setup.ps1' -Args (,('git', 'versioning', 'changelog', 'msbuild', 'nuget'))

$version = $env:release_version
$description = $env:release_description

# Git checkout master
Invoke-Git ('checkout', 'master') -Verbose

# Update AssembyInfo
Update-AssemblyInfo $assemblyInfoPath $version -Verbose

# Update CHANGELOG.md
Update-Changelog $changelogPath $version $description -Verbose

# Update Nuspec file
Update-Nuspec $nuspecPath $version $description -Verbose

# Build
Invoke-MSBuild $solution $msbuildProperties -Verbose

# Create nuget-package
New-Item -ItemType directory -Path $releaseDir | Out-Null
Invoke-NuGetPack $project $configuration $releaseDir -Verbose

# Git add changes
Invoke-Git ('add', $assemblyInfoPath) -Verbose
Invoke-Git ('add', $changelogPath) -Verbose
Invoke-Git ('add', $nuspecPath) -Verbose

# Git commit and push
Invoke-GitCommit "Winium WebDriver version $version" -Verbose
Invoke-Git ('push', 'origin', 'master') -Verbose

# Git tag and push
$buildUrl = $env:BUILD_URL
Invoke-GitTag "Winium WebDriver version '$version'. Build url '$buildUrl'." "v$version" -Verbose
Invoke-Git ('push', 'origin', 'master', "v$version") -Verbose

# Push nuget-package
$package = Join-Path $releaseDir '*.nupkg'
Invoke-NuGetPush $package -Verbose
