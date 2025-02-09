package com.example.weather.repository;

import com.example.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findByPincodeAndForDate(String pincode, LocalDate forDate);
}
