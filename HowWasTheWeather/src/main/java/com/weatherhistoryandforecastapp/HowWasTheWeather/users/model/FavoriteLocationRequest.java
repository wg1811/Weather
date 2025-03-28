package com.weatherhistoryandforecastapp.HowWasTheWeather.users.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public class FavoriteLocationRequest {
    @NotBlank(message = "Location name cannot be empty")
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;

    // Constructors
    public FavoriteLocationRequest() {
    }

    public FavoriteLocationRequest(String name, BigDecimal longitude, BigDecimal latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

}