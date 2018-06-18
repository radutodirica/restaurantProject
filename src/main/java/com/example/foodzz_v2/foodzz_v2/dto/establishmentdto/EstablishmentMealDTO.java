package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class EstablishmentMealDTO {

    public EstablishmentMealDTO(){}

    public String getMealContent() {
        return mealContent;
    }

    public void setMealContent(String mealContent) {
        this.mealContent = mealContent;
    }

    public String getMealUUID() {
        return mealUUID;
    }

    public void setMealUUID(String mealUUID) {
        this.mealUUID = mealUUID;
    }

    private String mealContent;
    private String mealUUID;
}
