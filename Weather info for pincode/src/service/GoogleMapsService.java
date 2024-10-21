package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    public double[] getLatLongForPincode(String pincode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", pincode, googleApiKey);
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);

        // Extract latitude and longitude from JSON response
        double latitude = json.getJSONArray("results").getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        double longitude = json.getJSONArray("results").getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        return new double[]{latitude, longitude};
    }
}
