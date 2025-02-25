package com.weatherhistoryandforecastapp.HowWasTheWeather;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.Weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WeatherApp {

	// This is how you add a logger.  See docs for info on different levels
	private	static final Logger log = LoggerFactory.getLogger(WeatherApp.class);

	public static void main(String[] args) {
		SpringApplication.run(WeatherApp.class, args);
	}

	// @Bean
	// CommandLineRunner theWeather() {
	// 	return args -> {
	// 		Weather weather = new Weather(1, 40.7128, 74.0060, java.time.LocalDateTime.now(), java.time.LocalDateTime.now().plusDays(1));
	// 		log.info("The weather is nice today here at " + weather);
	// 	};
	// }

}
