# Menu.X Prerequisites Installation Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host " Menu.X Prerequisites Installation" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if running as administrator
$isAdmin = ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]"Administrator")
if (-not $isAdmin) {
    Write-Host "This script needs to be run as Administrator." -ForegroundColor Yellow
    Write-Host "Please close this window, right-click PowerShell, and select 'Run as Administrator'." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# 1. Check for Java
Write-Host "Checking for Java..." -ForegroundColor Blue
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    if ($javaVersion) {
        Write-Host "✓ Java is installed." -ForegroundColor Green
    } else {
        throw "Java not found"
    }
}
catch {
    Write-Host "✗ Java is not installed or not in your PATH." -ForegroundColor Red
    Write-Host "Please install Java 17+ from https://adoptium.net/ and try again." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# 2. Check for Maven
Write-Host "Checking for Maven..." -ForegroundColor Blue
if (Get-Command mvn -ErrorAction SilentlyContinue) {
    Write-Host "✓ Maven is already installed." -ForegroundColor Green
    Write-Host "All prerequisites are installed! You can now run the main application." -ForegroundColor Green
    Read-Host "Press Enter to exit"
    exit 0
} else {
    Write-Host "i Maven not found. Attempting to install it now..." -ForegroundColor Yellow
}

# 3. Install Maven using Chocolatey
try {
    if (-not (Get-Command choco -ErrorAction SilentlyContinue)) {
        Write-Host "i Chocolatey not found. Installing it first..." -ForegroundColor Yellow
        Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
        Write-Host "✓ Chocolatey installed successfully. Please close and re-open this Administrator PowerShell and run the script again." -ForegroundColor Green
        Read-Host "Press Enter to exit"
        exit 0
    }
    Write-Host "Installing Maven using Chocolatey..." -ForegroundColor Blue
    choco install maven -y
    Write-Host "✓ Maven installed successfully!" -ForegroundColor Green
}
catch {
    Write-Host "✗ An error occurred during installation." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Installation Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "IMPORTANT: Please close this PowerShell window and open a NEW one as Administrator." -ForegroundColor Yellow
Read-Host "Press Enter to exit"