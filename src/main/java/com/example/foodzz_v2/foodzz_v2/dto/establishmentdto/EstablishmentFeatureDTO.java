package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class EstablishmentFeatureDTO {

    public EstablishmentFeatureDTO(){}

    public String getFeatureContent() {
        return featureContent;
    }

    public void setFeatureContent(String featureContent) {
        this.featureContent = featureContent;
    }

    public String getFeatureUUID() {
        return featureUUID;
    }

    public void setFeatureUUID(String featureUUID) {
        this.featureUUID = featureUUID;
    }

    private String featureContent;
    private String featureUUID;
}
