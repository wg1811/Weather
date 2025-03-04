package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common;

public record Coordinates(double lat, double lng) {
    // Compact constructor (for additional logic, if needed)
    public Coordinates {
        // You can add validation logic here if needed
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }
}
