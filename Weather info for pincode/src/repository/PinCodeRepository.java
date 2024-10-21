package com.example.weather.repository;

import com.example.weather.entity.PincodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeRepository extends JpaRepository<PincodeEntity, String> {
}
