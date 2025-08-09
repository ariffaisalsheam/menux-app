# Clears the screen for better readability
Clear-Host

Write-Host "========================================" -ForegroundColor Cyan
Write-Host " Starting Menu.X Application" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if the database password environment variable is set
if (-not (Test-Path Env:DB_PASSWORD)) {
    Write-Host "ERROR: Database password is not set!" -ForegroundColor Red
    Write-Host 'Please set it first by running: $env:DB_PASSWORD = "your_password"' -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
} else {
    Write-Host "? Database password found." -ForegroundColor Green
}

# --- Start Backend Server ---
try {
    Write-Host "Starting Backend Server in a new window..." -ForegroundColor Blue
    $backendCommand = "cd backend; ./mvnw.cmd spring-boot:run"
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $backendCommand
    Write-Host "? Backend process started." -ForegroundColor Green
}
catch {
    Write-Host "FAILED to start backend." -ForegroundColor Red
    Write-Host $_
    Read-Host "Press Enter to exit"
    exit 1
}

# --- Start Frontend Server ---
try {
    Write-Host "Starting Frontend Server in a new window (please wait a moment)..." -ForegroundColor Blue
    # Add a small delay to let the backend start up first
    Start-Sleep -Seconds 10
    $frontendCommand = "cd frontend; npm start"
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $frontendCommand
    Write-Host "? Frontend process started." -ForegroundColor Green
}
catch {
    Write-Host "FAILED to start frontend." -ForegroundColor Red
    Write-Host $_
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "?? All services are starting up in new windows!" -ForegroundColor Green
Write-Host "You can access the application at http://localhost:3000" -ForegroundColor White
Write-Host ""
