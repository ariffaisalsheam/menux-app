# Menu.X Quick Start Guide

## Issues Fixed ✅
- ✅ **JWT Library Compatibility** - Updated to use latest JWT API
- ✅ **Maven Wrapper Added** - No need to install Maven separately
- ✅ **Simplified Startup Script** - `start-menux-simple.ps1`

## Quick Start (2 Steps)

### Step 1: Set Your Supabase Password
```powershell
$env:SUPABASE_DB_PASSWORD="your_actual_supabase_password"
```

### Step 2: Run the Simple Startup Script
```powershell
.\start-menux-simple.ps1
```

## What This Does
1. **Checks Java** - Verifies Java 17+ is installed
2. **Uses Maven Wrapper** - Downloads Maven automatically (no installation needed)
3. **Starts Backend** - Runs Spring Boot with Supabase configuration
4. **Installs Frontend Dependencies** - Runs `npm install` if needed
5. **Starts Frontend** - Launches React development server
6. **Opens Browser** - Automatically opens http://localhost:3000

## Expected Behavior
- Backend starts on port 8080 (may take 2-3 minutes first time)
- Frontend starts on port 3000
- Two command windows will open (backend and frontend)
- Browser should open automatically to http://localhost:3000

## Login Credentials
- **Admin**: admin@menux.com / password123
- **Restaurant Owner**: owner1@restaurant.com / password123

## If You Still Get Maven Errors

### Option 1: Install Maven (Recommended)
Run as Administrator:
```powershell
.\install-prerequisites.ps1
```

### Option 2: Manual Maven Installation
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Add `C:\Program Files\Apache\maven\bin` to your PATH
4. Restart PowerShell
5. Run: `.\start-menux.ps1`

### Option 3: Use IDE
1. Open `backend` folder in IntelliJ IDEA or Eclipse
2. Import as Maven project
3. Run `MenuXApplication.java` with profile `supabase`
4. In separate terminal: `cd frontend && npm install && npm start`

## Troubleshooting

### "Java not found"
- Install Java 17+ from: https://adoptium.net/
- Restart PowerShell after installation

### "Port already in use"
- Kill processes using ports 8080 or 3000
- Or change ports in configuration

### "Database connection failed"
- Verify your Supabase password is correct
- Check your internet connection

### "Frontend won't start"
- Ensure Node.js 18+ is installed
- Delete `frontend/node_modules` and run `npm install`

## Next Steps After Startup
1. Open http://localhost:3000
2. Login with admin@menux.com / password123
3. Explore the admin dashboard
4. Create test restaurants and menus
5. Test QR code generation
6. Review API documentation at http://localhost:8080/api/swagger-ui.html

## Development Workflow
- Backend auto-reloads on code changes
- Frontend hot-reloads on code changes
- Database is already configured with sample data
- API documentation updates automatically

Your Menu.X system should now be running successfully! 🚀
