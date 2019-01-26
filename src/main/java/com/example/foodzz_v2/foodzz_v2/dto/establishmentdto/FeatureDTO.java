package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class FeatureDTO {

    private String featureContent;
    private String featureUUID;
    private String establishmentUUID;

    public FeatureDTO(){}

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

    public String getEstablishmentUUID() {
        return establishmentUUID;
    }

    public void setEstablishmentUUID(String establishmentUUID) {
        this.establishmentUUID = establishmentUUID;
    }
}
