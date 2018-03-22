package com.example.foodzz_v2.foodzz_v2.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RestaurantDTO {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public RestaurantDTO(String response){
        Gson gson = new Gson();
        this.restaurantDTO = gson.fromJson(response,RestaurantDTO.class);
    }

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

    private RestaurantDTO restaurantDTO;

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;
}
