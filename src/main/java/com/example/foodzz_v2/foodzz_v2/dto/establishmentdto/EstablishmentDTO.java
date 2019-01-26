package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;

import java.util.Set;

public class EstablishmentDTO {

    private String name;
    private String establishmentUUID;
    private String description;
    private String cuisine;
    private double latitude;
    private double longitude;
    private String city;
    private String county;
    private String country;
    private double establishmentRaiting;
    private double userRaiting;
    private Set<FeatureDTO> features;
    private Set<CategoryDTO> categoryList;

    public EstablishmentDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEstablishmentUUID() {
        return establishmentUUID;
    }

    public void setEstablishmentUUID(String establishmentUUID) {
        this.establishmentUUID = establishmentUUID;
    }

    public double getEstablishmentRaiting() {
        return establishmentRaiting;
    }

    public void setEstablishmentRaiting(double establishmentRaiting) {
        this.establishmentRaiting = establishmentRaiting;
    }

    public double getUserRaiting() {
        return userRaiting;
    }

    public void setUserRaiting(double userRaiting) {
        this.userRaiting = userRaiting;
    }

    public Set<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(Set<FeatureDTO> features) {
        this.features = features;
    }

    public Set<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }
}
