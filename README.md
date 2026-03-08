# Carbon Footprint Tracker - Backend

This repository contains the backend API for the Carbon Footprint Tracker application.

## Tech Stack
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- JPA / Hibernate

## Features
- User registration and login
- JWT authentication
- Track daily activities
- Automatic carbon emission calculation
- User-specific activity tracking
- Dashboard analytics APIs

## API Endpoints

### Authentication
POST /auth/register  
POST /auth/login

### Activities
POST /activity  
GET /activity/my  
GET /activity/total

### Dashboard
GET /activity/category-summary  
GET /activity/monthly

## Authentication
All protected APIs require:

Authorization: Bearer <JWT_TOKEN>

## Database
MySQL database used with JPA entities.

## Future Improvements
- React dashboard
- Data visualization
- Carbon reduction recommendations