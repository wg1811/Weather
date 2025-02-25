// package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

// import java.io.Console;
// import java.net.http.HttpClient;

// import com.fasterxml.jackson.databind.JsonSerializer;

// public class WeatherService {
     
//         public async Task<string?> TestWeather(
//             double lat,
//             double lng,
//             string startDate,
//             string endDate
//         )
//         {
//             try
//             {
//                 using var client = new HttpClient();
//                 var weatherUrl =
//                     $"https://archive-api.open-meteo.com/v1/archive"
//                     + $"?latitude={lat}&longitude={lng}"
//                     + $"&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration"
//                     + $"&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm"
//                     + $"&timezone=auto"
//                     + $"&start_date={startDate}"
//                     + $"&end_date={endDate}";

//                 var response = await client.GetAsync(weatherUrl);

//                 return response.IsSuccessStatusCode
//                     ? await response.Content.ReadAsStringAsync()
//                     : null;
//             }
//             catch (Exception ex)
//             {
//                 Console.WriteLine($"An error occurred: {ex.Message}");
//                 return null;
//             }
//         }

//         public async Task<WeatherResponse?> GetHistoricalWeather(
//             double lat,
//             double lng,
//             string startDate,
//             string endDate
//         )
//         {
//             try
//             {
//                 using var client = new HttpClient();
//                 var weatherUrl =
//                     $"https://archive-api.open-meteo.com/v1/archive"
//                     + $"?latitude={lat}&longitude={lng}"
//                     + $"&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration"
//                     + $"&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm"
//                     + $"&timezone=auto"
//                     + $"&start_date={startDate:yyyy-MM-dd}"
//                     + $"&end_date={endDate:yyyy-MM-dd}";

//                 var response = await client.GetAsync(weatherUrl);

//                 if (!response.IsSuccessStatusCode)
//                 {
//                     Console.WriteLine(
//                         $"API request failed with status code: {response.StatusCode}"
//                     );
//                     return null;
//                 }

//                 var jsonResponse = await response.Content.ReadAsStringAsync();
//                 Console.WriteLine($"Raw API response: {jsonResponse}"); // Is it working?

//                 var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };

//                 var weatherResponse = JsonSerializer.Deserialize<WeatherResponse>(
//                     jsonResponse,
//                     options
//                 );

//                 if (weatherResponse == null)
//                 {
//                     Console.WriteLine("Failed to deserialize weather response");
//                     return null;
//                 }

//                 return weatherResponse;
//             }
//             catch (Exception ex)
//             {
//                 Console.WriteLine($"An error occurred: {ex.Message}");
//                 return null;
//             }
//         }
//     }
// }
// }
// app.MapGet(
//     "/api/getweather",
//     async (double lat, double lon, string start_date, string end_date) =>
//     {
//         // Console.WriteLine(
//         //     $"The lat is {lat}, the long is {lon}.\nThe start date is {start_date}, the end date is {end_date}."
//         // );
//         WeatherService weatherService = new();
//         var weather = await weatherService.GetHistoricalWeather(lat, lon, start_date, end_date);
//         return weather != null
//             ? Results.Json(weather)
//             : Results.Problem("Weather data not fetched.");
//     }
// );

// app.MapGet(
//     "/api/testweather",
//     async (double lat, double lon, string start_date, string end_date) =>
//     {
//         WeatherService weatherService = new();
//         var weather = await weatherService.TestWeather(lat, lon, start_date, end_date);
//         return weather != null
//             ? Results.Json(weather)
//             : Results.Problem("Weather data not fetched.");
//     }
// );

// app.MapGet(
//     "/api/getMapsApiKey",
//     (IConfiguration config) =>
//     {
//         var apiKey = config["GoogleMaps:ApiKey"];
//         return Results.Json(new { apiKey });
//     }
// );

// // Define the minimal API route
// app.MapGet(
//     "/api/getCoordinates",
//     async (string address, HttpClient httpClient) =>
//     {
//         var apiKey = apiMapKey; // Your Google Maps API Key
//         var url =
//             $"https://maps.googleapis.com/maps/api/geocode/json?address={Uri.EscapeDataString(address)}&key={apiKey}";
//         var response = await httpClient.GetAsync(url);

//         if (!response.IsSuccessStatusCode)
//         {
//             return Results.BadRequest("Failed to get coordinates.");
//         }

//         var json = await response.Content.ReadAsStringAsync();
//         var jsonDocument = JsonDocument.Parse(json);
//         var results = jsonDocument.RootElement.GetProperty("results");

//         if (results.GetArrayLength() == 0)
//         {
//             return Results.NotFound("No results found for the given address.");
//         }

//         var location = results[0].GetProperty("geometry").GetProperty("location");
//         var lat = location.GetProperty("lat").GetDouble();
//         var lng = location.GetProperty("lng").GetDouble();

//         return Results.Ok(new { lat, lng });
//     }
// );
