package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.User;

import java.util.Set;

public class EstablishmentDTO {

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

    public Set<Integer> getRating() {
        return rating;
    }

    public void setRating(Set<Integer> rating) {
        this.rating = rating;
    }

    public Set<EstablishmentCommentDTO> getEstablishmentComments() {
        return establishmentComments;
    }

    public void setEstablishmentComments(Set<EstablishmentCommentDTO> establishmentComments) {
        this.establishmentComments = establishmentComments;
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

    public String getCityUUID() {
        return cityUUID;
    }

    public void setCityUUID(String cityUUID) {
        this.cityUUID = cityUUID;
    }

    public String getCountyUUID() {
        return countyUUID;
    }

    public void setCountyUUID(String countyUUID) {
        this.countyUUID = countyUUID;
    }

    public String getCountryUUID() {
        return countryUUID;
    }

    public void setCountryUUID(String countryUUID) {
        this.countryUUID = countryUUID;
    }

    public EstablishmentPriceDTO getEstablishmentPrice() {
        return establishmentPrice;
    }

    public void setEstablishmentPrice(EstablishmentPriceDTO establishmentPrice) {
        this.establishmentPrice = establishmentPrice;
    }

    public Set<EstablishmentFeatureDTO> getEstablishmentFeatures() {
        return establishmentFeatures;
    }

    public void setEstablishmentFeatures(Set<EstablishmentFeatureDTO> establishmentFeatures) {
        this.establishmentFeatures = establishmentFeatures;
    }

    public Set<EstablishmentMealDTO> getEstablishmentMeals() {
        return establishmentMeals;
    }

    public void setEstablishmentMeals(Set<EstablishmentMealDTO> establishmentMeals) {
        this.establishmentMeals = establishmentMeals;
    }

    public Set<EstablishmentQualityDTO> getEstablishmentQualities() {
        return establishmentQualities;
    }

    public void setEstablishmentQualities(Set<EstablishmentQualityDTO> establishmentQualities) {
        this.establishmentQualities = establishmentQualities;
    }

    private String name;
    private String description;
    private String cuisine;
    private Set<Integer> rating;
    private Set<EstablishmentCommentDTO> establishmentComments;
    private double latitude;
    private double longitude;
    private String cityUUID;
    private String countyUUID;
    private String countryUUID;
    private EstablishmentPriceDTO establishmentPrice;
    private Set<EstablishmentFeatureDTO> establishmentFeatures;
    private Set<EstablishmentMealDTO> establishmentMeals;
    private Set<EstablishmentQualityDTO> establishmentQualities;
}
