package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class MealDTO {

    private String mealContent;
    private String mealUUID;

    public MealDTO(){}

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

}
