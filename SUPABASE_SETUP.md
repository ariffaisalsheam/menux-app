# Menu.X Supabase Setup Guide

## Overview
This guide will help you set up and run Menu.X locally using Supabase as the database backend.

## Prerequisites
- **Java 17+** - For Spring Boot backend
- **Node.js 18+** - For React frontend
- **Maven 3.6+** - For Java dependency management
- **Supabase Account** - Already configured

## Database Setup ✅

Your Supabase database has been configured with:
- **Project ID**: pcwuvpjuysurnguqlzok
- **Region**: ap-southeast-1
- **Database Host**: db.pcwuvpjuysurnguqlzok.supabase.co
- **Database Schema**: ✅ Created
- **Sample Data**: ✅ Inserted
- **Indexes**: ✅ Created
- **Triggers**: ✅ Created

## Required Configuration

### 1. Get Your Supabase Database Password

1. Go to [Supabase Dashboard](https://supabase.com/dashboard)
2. Select your "Menu.X" project
3. Go to Settings → Database
4. Copy your database password (you set this when creating the project)

### 2. Set Environment Variables

Create a `.env` file in the project root:

```bash
# Database Configuration
SUPABASE_DB_PASSWORD=your_actual_supabase_password

# JWT Configuration (optional - defaults provided)
JWT_SECRET=MenuXSecretKey2024ForProductionUseStrongerKey

# Gemini AI (already configured)
GEMINI_API_KEY=AIzaSyAuWkC4FLccs0EZs0prOufuBk2ojWaCb2E
```

## Running the Application

### Option 1: Quick Start (Recommended)

1. **Set your Supabase password**:
   ```bash
   # Windows (PowerShell)
   $env:SUPABASE_DB_PASSWORD="your_actual_password"
   
   # macOS/Linux
   export SUPABASE_DB_PASSWORD="your_actual_password"
   ```

2. **Start Backend**:
   ```bash
   cd backend
   mvn spring-boot:run -Dspring-boot.run.profiles=supabase
   ```

3. **Start Frontend** (in a new terminal):
   ```bash
   cd frontend
   npm install
   npm start
   ```

### Option 2: Using Configuration Files

1. **Update Backend Configuration**:
   Edit `backend/src/main/resources/application-supabase.yml` and replace:
   ```yaml
   password: ${SUPABASE_DB_PASSWORD:your_supabase_password}
   ```
   with your actual password.

2. **Run Backend**:
   ```bash
   cd backend
   mvn spring-boot:run -Dspring-boot.run.profiles=supabase
   ```

3. **Run Frontend**:
   ```bash
   cd frontend
   npm install
   npm start
   ```

## Application URLs

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **API Documentation**: http://localhost:8080/api/swagger-ui.html
- **Health Check**: http://localhost:8080/api/actuator/health

## Default Login Credentials

### Super Admin
- **Email**: admin@menux.com
- **Password**: password123

### Restaurant Owners
- **Email**: owner1@restaurant.com
- **Password**: password123

- **Email**: owner2@restaurant.com  
- **Password**: password123

- **Email**: owner3@restaurant.com
- **Password**: password123

## Sample Data Included

Your database now contains:
- ✅ 1 Super Admin user
- ✅ 3 Restaurant Owner users
- ✅ 3 Sample restaurants (1 Free, 2 Pro)
- ✅ Menu categories and items
- ✅ Sample orders and feedback

## Verification Steps

1. **Check Backend Health**:
   ```bash
   curl http://localhost:8080/api/actuator/health
   ```

2. **Test Login API**:
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"admin@menux.com","password":"password123"}'
   ```

3. **Access Frontend**: Open http://localhost:3000

## Troubleshooting

### Database Connection Issues
- Verify your Supabase password is correct
- Check if your IP is allowed in Supabase (should be by default)
- Ensure the environment variable is set correctly

### Backend Won't Start
- Check Java version: `java -version` (should be 17+)
- Verify Maven installation: `mvn -version`
- Check application logs for specific errors

### Frontend Issues
- Verify Node.js version: `node -version` (should be 18+)
- Clear npm cache: `npm cache clean --force`
- Delete node_modules and reinstall: `rm -rf node_modules && npm install`

### Common Error Solutions

1. **"Connection refused"**:
   - Check if Supabase password is correct
   - Verify network connectivity

2. **"JWT token invalid"**:
   - Check JWT secret configuration
   - Clear browser localStorage

3. **"CORS error"**:
   - Verify frontend URL in CORS configuration
   - Check if backend is running on port 8080

## Development Workflow

1. **Backend Development**:
   - Code changes auto-reload with Spring Boot DevTools
   - API docs update automatically at `/swagger-ui.html`

2. **Frontend Development**:
   - React hot reload enabled
   - Changes reflect immediately in browser

3. **Database Changes**:
   - Use Supabase Dashboard for quick queries
   - Or connect via any PostgreSQL client

## Production Deployment

For production deployment:
1. Update CORS origins in `application-supabase.yml`
2. Use strong JWT secret
3. Configure proper environment variables
4. Set up SSL/HTTPS
5. Configure Supabase production settings

## Support

If you encounter issues:
1. Check the logs in terminal
2. Verify all environment variables
3. Ensure Supabase project is active
4. Check network connectivity

## Next Steps

Now that the system is running:
1. Explore the admin dashboard at http://localhost:3000 (login as admin)
2. Create test restaurants and menus
3. Test QR code generation
4. Explore the API documentation
5. Start customizing features for your needs

Your Menu.X system is now ready for development! 🚀
