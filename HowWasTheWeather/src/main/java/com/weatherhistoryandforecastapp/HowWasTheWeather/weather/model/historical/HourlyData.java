package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical;

public record HourlyData(
        String[] time,
        Double[] temperature2m,
        int[] weatherCode,
        Double[] relativeHumidity2m,
        Double[] dewPoint2m,
        Double[] apparentTemperature,
        Double[] precipitation,
        Double[] pressureMsl,
        Double[] cloudCover,
        Double[] visibility,
        Double[] windSpeed10m,
        Integer[] windDirection10m,
        Double[] windGusts10m,
        Double[] soilTemperature0cm,
        Double[] soilMoisture0_1cm) {
}

class HourlyUnits {
    public String time = "";
    public String temperature2m = "°C";
    public String weatherCode = "";
    public String relativeHumidity2m = "%";
    public String dewPoint2m = "°C";
    public String apparentTemperature = "°C";
    public String precipitation = "mm";
    public String pressureMsl = "hPa";
    public String cloudCover = "%";
    public String visibility = "m";
    public String windSpeed10m = "m/s";
    public String windDirection10m = "°";
    public String windGusts10m = "m/s";
    public String soilTemperature0cm = "°C";
    public String soilMoisture0_1cm = "m³/m³";
}
