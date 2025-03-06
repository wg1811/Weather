package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical;

import java.util.List;

public record HourlyData(
        List<String> time,
        List<Double> temperature_2m,
        List<Double> relative_humidity_2m,
        List<Double> dewPoint2m,
        List<Double> apparent_temperature,
        List<Double> precipitation,
        List<Double> rain,
        List<Double> snowfall,
        List<Double> snow_depth,
        List<Integer> weather_code,
        List<Double> pressure_msl,
        List<Double> surface_pressure,
        List<Integer> cloud_cover,
        List<Double> wind_speed_10m,
        List<Integer> wind_direction_10m,
        List<Integer> is_day,
        List<Double> sunshine_duration) {
}

class HourlyUnits {
    public String time = "";
    public String temperature_2m = "°C";
    public String relative_humidity_2m = "%";
    public String dewPoint2m = "°C";
    public String apparent_temperature = "°C";
    public String precipitation = "mm";
    public String rain = "mm";
    public String snowfall = "cm";
    public String snow_depth = "m";
    public String weather_code = "";
    public String pressure_msl = "hPa";
    public String surface_pressure = "hPa";
    public String cloud_cover = "%";
    public String wind_speed_10m = "km/h";
    public String wind_direction_10m = "°";
    public String is_day = "";
    public String sunshine_duration = "min";
}
