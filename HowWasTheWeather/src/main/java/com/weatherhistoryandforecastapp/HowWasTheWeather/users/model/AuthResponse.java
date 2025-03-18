package com.weatherhistoryandforecastapp.HowWasTheWeather.users.model;

import lombok.Data;  //  Not sure what this is for...might be for MongoDB, so won't need it.

@Data
public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

