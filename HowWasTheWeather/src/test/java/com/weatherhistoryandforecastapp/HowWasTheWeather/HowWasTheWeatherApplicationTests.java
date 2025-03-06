package com.weatherhistoryandforecastapp.HowWasTheWeather;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HowWasTheWeatherApplicationTests {

	@Test
	void contextLoads() {
	}

	    // @GetMapping("/testgetweather")
    // public void testGetWeather(@RequestParam String location, @RequestParam String startDate,
    //         @RequestParam String endDate) {
    //     Mono<Coordinates> coordinates = geocodeService.getCoordinates(location);
    //     coordinates.subscribe(coords -> weatherService.GetWeatherTest(coords, startDate, endDate));
    // }


}
