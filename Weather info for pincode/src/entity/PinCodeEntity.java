package com.example.weather.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PincodeEntity {

    @Id
    private String pincode;
    private double latitude;
    private double longitude;

    // Getters and Setters
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
