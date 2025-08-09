@echo off
echo ========================================
echo Menu.X - Digital Restaurant System
echo ========================================
echo.

REM Check if Supabase password is set
if "%SUPABASE_DB_PASSWORD%"=="" (
    echo ERROR: SUPABASE_DB_PASSWORD environment variable is not set!
    echo.
    echo Please set your Supabase database password:
    echo set SUPABASE_DB_PASSWORD=your_actual_password
    echo.
    echo Then run this script again.
    pause
    exit /b 1
)

echo Starting Menu.X with Supabase configuration...
echo.

REM Start backend in a new window
echo Starting Backend Server...
start "Menu.X Backend" cmd /k "cd backend && mvn spring-boot:run -Dspring-boot.run.profiles=supabase"

REM Wait a moment for backend to start
timeout /t 5 /nobreak > nul

REM Start frontend in a new window
echo Starting Frontend Server...
start "Menu.X Frontend" cmd /k "cd frontend && npm install && npm start"

echo.
echo ========================================
echo Menu.X is starting up!
echo ========================================
echo.
echo Backend will be available at: http://localhost:8080/api
echo Frontend will be available at: http://localhost:3000
echo API Documentation: http://localhost:8080/api/swagger-ui.html
echo.
echo Default login credentials:
echo Admin: admin@menux.com / password123
echo Restaurant Owner: owner1@restaurant.com / password123
echo.
echo Press any key to exit this window...
pause > nul
