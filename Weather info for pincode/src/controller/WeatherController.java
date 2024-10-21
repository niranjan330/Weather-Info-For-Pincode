package com.example.weather.controller;

import com.example.weather.entity.PincodeEntity;
import com.example.weather.entity.WeatherEntity;
import com.example.weather.repository.PincodeRepository;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.GoogleMapsService;
import com.example.weather.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private OpenWeatherService openWeatherService;

    @GetMapping
    public ResponseEntity<WeatherEntity> getWeatherByPincodeAndDate(
        @RequestParam String pincode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) {

        // Check if weather data is available in DB
        Optional<WeatherEntity> weatherOpt = weatherRepository.findByPincodeAndForDate(pincode, forDate);
        if (weatherOpt.isPresent()) {
            return ResponseEntity.ok(weatherOpt.get());
        }

        // Get lat-long for the pincode (Check DB first, then Google Maps API)
        PincodeEntity pincodeEntity = pincodeRepository.findById(pincode).orElseGet(() -> {
            double[] latLong = googleMapsService.getLatLongForPincode(pincode);
            PincodeEntity newPincodeEntity = new PincodeEntity();
            newPincodeEntity.setPincode(pincode);
            newPincodeEntity.setLatitude(latLong[0]);
            newPincodeEntity.setLongitude(latLong[1]);
            return pincodeRepository.save(newPincodeEntity);
        });

        // Fetch weather information
        WeatherEntity weatherEntity = openWeatherService.getWeatherForLatLong(
            pincodeEntity.getLatitude(),
            pincodeEntity.getLongitude(),
            pincode,
            forDate
        );

        // Save weather data to DB
        weatherRepository.save(weatherEntity);

        return ResponseEntity.ok(weatherEntity);
    }
}
