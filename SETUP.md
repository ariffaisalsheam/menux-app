# Menu.X Setup Guide

## Overview
This guide will help you set up and run the complete Menu.X Digital Restaurant Communication System.

## Prerequisites

### Required Software
- **Java 17+** - For Spring Boot backend
- **Node.js 18+** - For React frontend
- **PostgreSQL 13+** - For database
- **Maven 3.6+** - For Java dependency management

### Optional Tools
- **Docker** - For containerized deployment
- **Git** - For version control
- **IDE** - IntelliJ IDEA, VS Code, or similar

## Database Setup

### 1. Install PostgreSQL
```bash
# On Windows (using Chocolatey)
choco install postgresql

# On macOS (using Homebrew)
brew install postgresql

# On Ubuntu/Debian
sudo apt-get install postgresql postgresql-contrib
```

### 2. Create Database and User
```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database
CREATE DATABASE menux_db;

-- Create user
CREATE USER menux_user WITH PASSWORD 'menux_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE menux_db TO menux_user;

-- Exit
\q
```

### 3. Run Database Schema
```bash
# Navigate to database directory
cd database

# Run schema creation
psql -U menux_user -d menux_db -f schema.sql

# Run sample data (optional)
psql -U menux_user -d menux_db -f sample-data.sql
```

## Backend Setup

### 1. Navigate to Backend Directory
```bash
cd backend
```

### 2. Configure Application Properties
Update `src/main/resources/application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/menux_db
    username: menux_user
    password: menux_password
```

### 3. Install Dependencies and Run
```bash
# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run

# Or run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The backend will start on `http://localhost:8080`

### 4. Verify Backend
- API Documentation: `http://localhost:8080/api/swagger-ui.html`
- Health Check: `http://localhost:8080/api/actuator/health`

## Frontend Setup

### 1. Navigate to Frontend Directory
```bash
cd frontend
```

### 2. Install Dependencies
```bash
# Using npm
npm install

# Or using yarn
yarn install
```

### 3. Configure Environment Variables
Create `.env` file in frontend directory:

```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_APP_NAME=Menu.X
```

### 4. Start Development Server
```bash
# Using npm
npm start

# Or using yarn
yarn start
```

The frontend will start on `http://localhost:3000`

## Environment Configuration

### Development Environment
- Backend runs on port 8080
- Frontend runs on port 3000
- Database on default PostgreSQL port 5432

### Production Environment
Update the following for production:

1. **Database**: Use production PostgreSQL instance
2. **Security**: Update JWT secret and other security configurations
3. **CORS**: Configure allowed origins
4. **SSL**: Enable HTTPS
5. **Environment Variables**: Set production values

## API Configuration

### JWT Configuration
Update in `application.yml`:
```yaml
app:
  jwt:
    secret: your-production-secret-key
    expiration: 86400000  # 24 hours
```

### Google Gemini AI (Optional)
For AI features, add your Gemini API key:
```yaml
app:
  ai:
    gemini:
      api-key: your-gemini-api-key
```

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## Building for Production

### Backend
```bash
cd backend
mvn clean package
java -jar target/menu-x-backend-1.0.0.jar
```

### Frontend
```bash
cd frontend
npm run build
# Serve the build folder with a web server
```

## Docker Deployment (Optional)

### Create Dockerfile for Backend
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/menu-x-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Create Dockerfile for Frontend
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
```

### Docker Compose
```yaml
version: '3.8'
services:
  database:
    image: postgres:13
    environment:
      POSTGRES_DB: menux_db
      POSTGRES_USER: menux_user
      POSTGRES_PASSWORD: menux_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - database
    environment:
      DB_USERNAME: menux_user
      DB_PASSWORD: menux_password

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check PostgreSQL is running
   - Verify credentials in application.yml
   - Ensure database exists

2. **Port Already in Use**
   - Change ports in configuration
   - Kill existing processes

3. **CORS Issues**
   - Update CORS configuration in backend
   - Check frontend API base URL

4. **JWT Token Issues**
   - Verify JWT secret configuration
   - Check token expiration settings

### Logs
- Backend logs: Check console output or `logs/menu-x.log`
- Frontend logs: Check browser console
- Database logs: Check PostgreSQL logs

## Default Credentials

### Super Admin
- Email: `admin@menux.com`
- Password: `password123`

### Sample Restaurant Owner
- Email: `owner1@restaurant.com`
- Password: `password123`

## Next Steps

1. **Customize Branding**: Update logos, colors, and branding
2. **Configure Email**: Set up SMTP for notifications
3. **Add Payment Integration**: Implement payment processing
4. **Set up Monitoring**: Add application monitoring
5. **Configure Backups**: Set up database backups
6. **SSL Certificates**: Configure HTTPS for production

## Support

For issues and questions:
- Check the documentation
- Review error logs
- Contact the development team

## License

This project is licensed under the MIT License.
