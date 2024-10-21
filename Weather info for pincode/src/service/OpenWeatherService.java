package com.example.weather.service;

import com.example.weather.entity.WeatherEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.time.LocalDate;

@Service
public class OpenWeatherService {

    @Value("${openweather.api.key}")
    private String openWeatherApiKey;

    public WeatherEntity getWeatherForLatLong(double lat, double lon, String pincode, LocalDate forDate) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric",
                lat, lon, openWeatherApiKey);
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);

        // Extract weather information from JSON response
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = json.getJSONObject("main").getDouble("temp");
        double windSpeed = json.getJSONObject("wind").getDouble("speed");

        // Create and return WeatherEntity object
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setPincode(pincode);
        weatherEntity.setForDate(forDate);
        weatherEntity.setWeatherDescription(description);
        weatherEntity.setTemperature(temperature);
        weatherEntity.setWindSpeed(windSpeed);

        return weatherEntity;
    }
}
