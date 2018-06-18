package com.example.foodzz_v2.foodzz_v2.filter;


public class RestaurantFilterObject {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getCityUUID() {
        return cityUUID;
    }

    public void setCityUUID(String cityUUID) {
        this.cityUUID = cityUUID;
    }

    public RestaurantFilterObject(int page, int count, String countyUUID, String countryUUID, String cityUUID) {
        this.page = page;
        this.count = count;
        this.countyUUID = countyUUID;
        this.countryUUID = countryUUID;
        this.cityUUID = cityUUID;
    }

    private int page;
    private int count;
    private String countyUUID;
    private String countryUUID;
    private String cityUUID;
}
