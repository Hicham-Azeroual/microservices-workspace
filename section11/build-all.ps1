# Define the root directory (use single quotes to avoid escape issues)
$rootDir = 'C:\Users\EliteBook 840 G7\Desktop\microservices\section11'

# Define the list of microservices
$services = @('accounts', 'cards', 'configserver', 'eurekaserver', 'gatwayserver', 'loans')

foreach ($service in $services) {
    Write-Host "----------------------------------------"
    Write-Host "🚀 Building Docker image for $service ..."
    Write-Host "----------------------------------------"

    $path = Join-Path $rootDir $service
    if (Test-Path $path) {
        Set-Location $path
        mvn compile jib:dockerBuild

        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Successfully built image for $service" -ForegroundColor Green
        } else {
            Write-Host "❌ Failed to build image for $service" -ForegroundColor Red
        }

        # Return to the root folder
        Set-Location $rootDir
    } else {
        Write-Host "⚠️ Directory $service does not exist, skipping..." -ForegroundColor Yellow
    }

    Write-Host ""
}

Write-Host "🎯 All builds completed!" -ForegroundColor Cyan
