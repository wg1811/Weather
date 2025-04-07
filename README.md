# How Was The Weather

A full-stack web application for retrieving weather forecasts and managing user favorite locations. The backend is built with Java Spring Boot, providing RESTful APIs to fetch geolocation and weather data from external services, as well as user management features. The frontend is a modern single-page application (SPA) built with Vite, TypeScript, and styled using Tailwind CSS.

![Search and Current Weather](images/forecast-ui1.png "Search and Current Weather")

![Hourly Forecast](images/forecast-ui2.png "Hourly Forecast")

![Daily Forecast with Clickable Hourly Breakdown](images/forecast-ui1.png "Daily Forecast with Clickable Hourly Breakdown")

## Features
- **Geocoding**: Convert addresses to latitude/longitude coordinates using the Google Maps Geocoding API.
- **Weather Forecasts**: Retrieve current, hourly, and daily weather forecasts (up to 16 days) from the Open-Meteo API, including temperature, precipitation, wind, UV index, and more.
- **User Management**: Register and authenticate users with securely encoded passwords using JWT.
- **Favorite Locations**: Save, retrieve, and delete favorite locations for authenticated users.
- **Responsive UI**: A clean, modern frontend interface styled with Tailwind CSS.

## Tech Stack

### Backend
- **Java 17+** with Spring Boot
- **Spring WebFlux**: For reactive, non-blocking HTTP requests
- **WebClient**: For API calls to external services (Google Maps API, Open-Meteo API)
- **Spring Data R2DBC**: Reactive database access for user and favorite location persistence
- **Spring Security**: JWT-based authentication and password encoding
- **PostgreSQL**: Database for storing users and favorite locations
- **JUnit 5 & Mockito**: For unit testing
- **Maven**: Dependency management and build tool

### Frontend
- **Vite**: Fast build tool and development server
- **TypeScript**: Static typing for JavaScript
- **Tailwind CSS**: Utility-first CSS framework for responsive design

## Project Structure

```
weather-history-forecast-app/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/weatherhistoryandforecastapp/HowWasTheWeather/
│   │   │   │   ├── users/
│   │   │   │   │   ├── config/         # Configuration for user-related features
│   │   │   │   │   ├── controller/     # REST controllers for user endpoints (AuthController, FavoriteLocationController)
│   │   │   │   │   ├── model/          # User and FavoriteLocation models
│   │   │   │   │   ├── repository/     # R2DBC repositories (UserRepository, FavoriteLocationRepository)
│   │   │   │   │   ├── security/       # Security configuration (JWT, password encoding)
│   │   │   │   │   └── service/        # UserService, FavoriteLocationService
│   │   │   │   ├── weather/
│   │   │   │   │   ├── config/         # Configuration for weather-related features
│   │   │   │   │   ├── controller/     # REST controllers for weather endpoints (ForecastController)
│   │   │   │   │   ├── DTO/            # Data Transfer Objects for weather data
│   │   │   │   │   ├── model/          # Weather models (e.g., Coordinates, ForecastData)
│   │   │   │   │   └── service/        # GeocodeService, ForecastService
│   │   │   │   └── WeatherApp.java     # Main application entry point
│   │   │   └── resources/              # application.properties (API keys, DB config)
│   │   └── test/
│   │       └── java/com/weatherhistoryandforecastapp/HowWasTheWeather/
│   │           ├── users/
│   │           │   └── controller/     # Tests for user controllers
│   │           └── weather/            # Tests for weather services
│   └── pom.xml                         # Maven configuration
├── frontend/
│   ├── node_modules/                   # Node dependencies
│   ├── public/                         # Static assets
│   ├── src/
│   │   ├── api/                        # API client for backend communication
│   │   ├── assets/                     # Images, fonts, etc.
│   │   ├── components/
│   │   │   ├── auth/                   # Authentication components (e.g., login, register)
│   │   │   ├── common/                 # Reusable UI components
│   │   │   ├── user/                   # User-related components (e.g., favorite locations)
│   │   │   └── weather/                # Weather display components
│   │   ├── pages/                      # Page components (e.g., Home, Weather)
│   │   ├── services/                   # Frontend services (e.g., API calls)
│   │   ├── types/                      # TypeScript type definitions
│   │   ├── App.css                     # App-level styles
│   │   ├── App.tsx                     # Main App component
│   │   ├── index.css                   # Global styles
│   │   └── main.tsx                    # Entry point for Vite
│   ├── package.json                    # Node dependencies
│   ├── tailwind.config.js              # Tailwind configuration
│   ├── vite.config.ts                  # Vite configuration
│   └── tsconfig.json                   # TypeScript configuration
├── README.md                           # This file
```

## API Endpoints

### Authentication
- **POST /api/login**  
  Authenticate a user and return a JWT token.  
  **Request Body**: `{ "email": "string", "password": "string" }`  
  **Response**: `200 OK` with `{ "token": "jwt-token" }` or `401 Unauthorized` if credentials are invalid.

- **POST /api/signup**  
  Register a new user and return a JWT token.  
  **Request Body**: `{ "email": "string", "password": "string" }`  
  **Response**: `201 Created` with `{ "token": "jwt-token" }` or `400 Bad Request` if email is already in use.

### Favorite Locations
- **POST /api/favorites**  
  Add a new favorite location for the authenticated user.  
  **Request Body**: `{ "name": "string", "latitude": number, "longitude": number }`  
  **Headers**: `Authorization: Bearer <jwt-token>`  
  **Response**: `200 OK` with the saved `FavoriteLocation` or `401 Unauthorized` if not authenticated.

- **GET /api/favorites**  
  Retrieve all favorite locations for the authenticated user.  
  **Headers**: `Authorization: Bearer <jwt-token>`  
  **Response**: `200 OK` with an array of `FavoriteLocation` objects.

- **DELETE /api/favorites/{id}**  
  Delete a favorite location by ID.  
  **Path Parameter**: `id` (Long)  
  **Headers**: `Authorization: Bearer <jwt-token>`  
  **Response**: `204 No Content` on success, `404 Not Found` if the ID doesn’t exist.

### Weather Forecast
- **GET /api/forecast/hello**  
  Test endpoint to verify the forecast API is running.  
  **Response**: `200 OK` with `"Hello, world!"`.

- **GET /api/forecast/current**  
  Get current weather for a location.  
  **Query Parameter**: `location` (string, e.g., "New York")  
  **Response**: `200 OK` with a `CurrentWeatherDTO` object or `404 Not Found` if the location is invalid.

- **GET /api/forecast/hourly**  
  Get hourly weather forecast for a location.  
  **Query Parameter**: `location` (string)  
  **Response**: `200 OK` with a `HourlyForecastDTO` object or `404 Not Found` if the location is invalid.

- **GET /api/forecast/daily**  
  Get daily weather forecast for a location.  
  **Query Parameter**: `location` (string)  
  **Response**: `200 OK` with a `DailyForecastDTO` object or `404 Not Found` if the location is invalid.

- **GET /api/forecast/getforecast**  
  Get the full weather forecast (current, hourly, daily) for a location.  
  **Query Parameter**: `location` (string)  
  **Response**: `200 OK` with a `ForecastDTO` object or `404 Not Found` if the location is invalid.

## Prerequisites
- **Java 17+**: For the backend
- **Node.js 18+**: For the frontend
- **Maven**: For backend dependency management
- **PostgreSQL**: Database for storing users and favorite locations
- **API Keys**:
  - Google Maps API key (for geocoding)
  - No key required for Open-Meteo (free tier used)

## Setup

### Database (PostgreSQL)
1. Install PostgreSQL if not already installed.
2. Create a database for the app:
   ```sql
   CREATE DATABASE weather_app;
   ```
3. Ensure your PostgreSQL user has the necessary permissions.

### Backend
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd weather-history-forecast-app/backend
   ```
2. Configure environment variables in `src/main/resources/application.properties`:
   ```properties
   google.maps.api.key=<your-google-maps-api-key>
   spring.r2dbc.url=r2dbc:postgresql://localhost:5432/weather_app
   spring.r2dbc.username=<your-postgres-username>
   spring.r2dbc.password=<your-postgres-password>
   spring.sql.init.mode=always
   ```
3. (Optional) If you need to initialize the schema, create a `schema.sql` file in `src/main/resources`:
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       email VARCHAR(255) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL
   );

   CREATE TABLE favorite_location (
       id SERIAL PRIMARY KEY,
       user_id BIGINT NOT NULL,
       name VARCHAR(255) NOT NULL,
       latitude DECIMAL(9,6) NOT NULL,
       longitude DECIMAL(9,6) NOT NULL,
       created_at TIMESTAMP NOT NULL,
       FOREIGN KEY (user_id) REFERENCES users(id)
   );
   ```
4. Build and run the backend:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend
1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   npm run dev
   ```
   The app will be available at `http://localhost:5173` (or another port if configured).

## Usage
- Enter an address to get its coordinates and view current weather and hourly or daily forecasts.
- Register or log in to save favorite locations.
- View and manage your saved locations from the UI.

## Testing
- Backend tests are located in `backend/src/test`. Run them with:
  ```bash
  mvn test
  ```

## Contributing
Feel free to submit issues or pull requests. Ensure all tests pass and follow the existing code style.

## License
*(Add your preferred license here, e.g., MIT, Apache 2.0)*
