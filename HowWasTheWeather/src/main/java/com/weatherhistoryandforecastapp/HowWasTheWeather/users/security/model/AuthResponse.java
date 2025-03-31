package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model;

import lombok.Data; //  Not sure what this is for...might be for MongoDB, so won't need it.

@Data
public class AuthResponse {

    private String token;
    private String errorMessage; // Optional field for error messages

    public AuthResponse(String token) {
        this.token = token;
        this.errorMessage = null; 
    }
    // for errors
    public AuthResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage; 
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return errorMessage;
    }

    public void setError(String error) {
        this.errorMessage = error;
    }
}
