package com.weatherhistoryandforecastapp.HowWasTheWeather;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApp {

	// // I don't know how logging works, but this is how the guide started it
	// (above and below)
	// private static final Logger log = LoggerFactory.getLogger(WeatherApp.class);

	public static void main(String[] args) {
		SpringApplication.run(WeatherApp.class, args);
	}

	// @Bean
	// CommandLineRunner theWeather() {
	// return args -> {
	// Weather weather = new Weather(1, 40.7128, 74.0060,
	// java.time.LocalDateTime.now(), java.time.LocalDateTime.now().plusDays(1));
	// log.info("The weather is nice today here at " + weather);
	// };
	// }

}
