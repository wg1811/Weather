package com.weatherhistoryandforecastapp.HowWasTheWeather.users.model;

import jakarta.validation.constraints.NotBlank;

public class FavoriteLocationRequest {
    @NotBlank(message = "Location name cannot be empty")
    private String locationName;
    private Long Longitude;
    private Long Latitude;

    // Constructors
    public FavoriteLocationRequest() {
    }

    public FavoriteLocationRequest(String locationName, Long Longitude, Long Latitude) {
        this.locationName = locationName;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }

    // Getters and Setters
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getLongitude() {
        return Longitude;
    }

    public void setLongitude(Long Longitude) {
        this.Longitude = Longitude;
    }

    public Long getLatitude() {
        return Latitude;
    }

    public void setLatitude(Long Latitude) {
        this.Latitude = Latitude;
    }

}