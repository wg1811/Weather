package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical;

public record DailyData(
        String[] time,
        int[] weatherCode,
        Double[] temperature2mMax,
        Double[] temperature2mMin,
        Double[] apparentTemperatureMax,
        Double[] apparentTemperatureMin,
        Double[] precipitationSum,
        Double[] precipitationHours,
        Double[] pressureMslMean,
        String[] sunrise,
        String[] sunset,
        Double[] windSpeed10mMax,
        Double[] windGusts10mMax,
        Integer[] windDirection10mDominant,
        Double[] shortwaveRadiationSum,
        Double[] et0FaoEvapotranspiration) {
}

class DailyUnits {
    public String weatherCode = "";
    public String temperature2mMax = "°C";
    public String temperature2mMin = "°C";
    public String apparentTemperatureMax = "°C";
    public String apparentTemperatureMin = "°C";
    public String precipitationSum = "mm";
    public String precipitationHours = "h";
    public String pressureMslMean = "hPa";
    public String sunrise = "";
    public String sunset = "";
    public String windSpeed10mMax = "m/s";
    public String windGusts10mMax = "m/s";
    public String windDirection10mDominant = "°";
    public String shortwaveRadiationSum = "MJ/m²";
    public String et0FaoEvapotranspiration = "mm";
}
