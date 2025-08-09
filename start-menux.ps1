# Menu.X Startup Script for Windows PowerShell

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Menu.X - Digital Restaurant System" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Supabase password is set
if (-not $env:SUPABASE_DB_PASSWORD) {
    Write-Host "ERROR: SUPABASE_DB_PASSWORD environment variable is not set!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please set your Supabase database password:" -ForegroundColor Yellow
    Write-Host '$env:SUPABASE_DB_PASSWORD="your_actual_password"' -ForegroundColor Green
    Write-Host ""
    Write-Host "Then run this script again." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Starting Menu.X with Supabase configuration..." -ForegroundColor Green
Write-Host ""

# Function to check if a port is in use
function Test-Port {
    param([int]$Port)
    try {
        $connection = New-Object System.Net.Sockets.TcpClient
        $connection.Connect("localhost", $Port)
        $connection.Close()
        return $true
    }
    catch {
        return $false
    }
}

# Check if ports are available
if (Test-Port 8080) {
    Write-Host "WARNING: Port 8080 is already in use. Backend may not start properly." -ForegroundColor Yellow
}

if (Test-Port 3000) {
    Write-Host "WARNING: Port 3000 is already in use. Frontend may not start properly." -ForegroundColor Yellow
}

try {
    # Start backend
    Write-Host "Starting Backend Server..." -ForegroundColor Blue
    $backendJob = Start-Job -ScriptBlock {
        Set-Location $using:PWD
        Set-Location backend
        mvn spring-boot:run -Dspring-boot.run.profiles=supabase
    }

    # Wait a moment for backend to initialize
    Start-Sleep -Seconds 10

    # Start frontend
    Write-Host "Starting Frontend Server..." -ForegroundColor Blue
    $frontendJob = Start-Job -ScriptBlock {
        Set-Location $using:PWD
        Set-Location frontend
        npm install
        npm start
    }

    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Menu.X is starting up!" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Backend will be available at: http://localhost:8080/api" -ForegroundColor Green
    Write-Host "Frontend will be available at: http://localhost:3000" -ForegroundColor Green
    Write-Host "API Documentation: http://localhost:8080/api/swagger-ui.html" -ForegroundColor Green
    Write-Host ""
    Write-Host "Default login credentials:" -ForegroundColor Yellow
    Write-Host "Admin: admin@menux.com / password123" -ForegroundColor White
    Write-Host "Restaurant Owner: owner1@restaurant.com / password123" -ForegroundColor White
    Write-Host ""
    Write-Host "Waiting for services to start..." -ForegroundColor Blue

    # Wait for backend to be ready
    $backendReady = $false
    $attempts = 0
    while (-not $backendReady -and $attempts -lt 30) {
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:8080/api/actuator/health" -TimeoutSec 2 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200) {
                $backendReady = $true
                Write-Host "✓ Backend is ready!" -ForegroundColor Green
            }
        }
        catch {
            Start-Sleep -Seconds 2
            $attempts++
            Write-Host "." -NoNewline -ForegroundColor Yellow
        }
    }

    if (-not $backendReady) {
        Write-Host ""
        Write-Host "Backend is taking longer than expected to start. Check the backend terminal for errors." -ForegroundColor Yellow
    }

    # Wait for frontend to be ready
    $frontendReady = $false
    $attempts = 0
    while (-not $frontendReady -and $attempts -lt 20) {
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:3000" -TimeoutSec 2 -ErrorAction SilentlyContinue
            if ($response.StatusCode -eq 200) {
                $frontendReady = $true
                Write-Host "✓ Frontend is ready!" -ForegroundColor Green
            }
        }
        catch {
            Start-Sleep -Seconds 3
            $attempts++
            Write-Host "." -NoNewline -ForegroundColor Yellow
        }
    }

    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Menu.X is now running!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "You can now:" -ForegroundColor White
    Write-Host "1. Open http://localhost:3000 in your browser" -ForegroundColor White
    Write-Host "2. Login with admin@menux.com / password123" -ForegroundColor White
    Write-Host "3. Explore the system features" -ForegroundColor White
    Write-Host ""
    Write-Host "Press Ctrl+C to stop both services" -ForegroundColor Yellow
    Write-Host ""

    # Keep script running and monitor jobs
    while ($backendJob.State -eq "Running" -or $frontendJob.State -eq "Running") {
        Start-Sleep -Seconds 5
        
        if ($backendJob.State -eq "Failed") {
            Write-Host "Backend job failed!" -ForegroundColor Red
            Receive-Job $backendJob
            break
        }
        
        if ($frontendJob.State -eq "Failed") {
            Write-Host "Frontend job failed!" -ForegroundColor Red
            Receive-Job $frontendJob
            break
        }
    }
}
catch {
    Write-Host "Error starting Menu.X: $($_.Exception.Message)" -ForegroundColor Red
}
finally {
    # Cleanup jobs
    if ($backendJob) { Stop-Job $backendJob -ErrorAction SilentlyContinue; Remove-Job $backendJob -ErrorAction SilentlyContinue }
    if ($frontendJob) { Stop-Job $frontendJob -ErrorAction SilentlyContinue; Remove-Job $frontendJob -ErrorAction SilentlyContinue }
}
