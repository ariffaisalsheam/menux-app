# Menu.X - Digital Restaurant Menu Management and Communication System

## Overview
Menu.X is a comprehensive digital restaurant menu management and communication system that enables seamless interaction between diners, restaurant owners, and administrators through QR code-based menu access, digital ordering, and feedback management.

## Features

### For All Restaurants (Free & Pro)
- QR code-based menu access
- Digital menu viewing
- Customer feedback submission
- Menu management for restaurant owners
- QR code generation

### Pro Restaurant Features
- Digital order placement
- Bill request functionality
- Live order dashboard
- AI-powered menu description generation
- Advanced feedback analytics with sentiment analysis

### Super Admin Features
- User account management
- Pro access control
- System analytics and reporting

## Technology Stack
- **Frontend**: React with responsive design
- **Backend**: Java Spring Boot with RESTful APIs
- **Database**: PostgreSQL
- **AI Integration**: Google Gemini API
- **Authentication**: JWT-based security

## Project Structure
```
menu-x/
├── backend/                 # Spring Boot backend
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
├── frontend/               # React frontend
│   ├── src/
│   ├── public/
│   └── package.json
├── database/              # Database scripts
│   ├── schema.sql
│   └── sample-data.sql
└── docs/                  # Documentation
    ├── api/
    └── deployment/
```

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.6+
- Supabase Account (Database configured ✅)

### Quick Start with Supabase
1. Set your Supabase database password:
   ```bash
   # Windows PowerShell
   $env:SUPABASE_DB_PASSWORD="your_supabase_password"

   # macOS/Linux
   export SUPABASE_DB_PASSWORD="your_supabase_password"
   ```

2. Run the startup script:
   ```bash
   # Windows
   .\start-menux.ps1

   # Or use the batch file
   start-menux.bat
   ```

3. Access the application:
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api
   - API Docs: http://localhost:8080/api/swagger-ui.html

### Manual Setup
See [SUPABASE_SETUP.md](SUPABASE_SETUP.md) for detailed instructions.

### Default Login Credentials
- **Super Admin**: admin@menux.com / password123
- **Restaurant Owner**: owner1@restaurant.com / password123

## API Documentation
API documentation is available at `/swagger-ui.html` when the backend is running.

## License
This project is licensed under the MIT License.
