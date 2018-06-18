package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class EstablishmentQualityDTO {

    public EstablishmentQualityDTO(){}

    public String getQualityContent() {
        return qualityContent;
    }

    public void setQualityContent(String qualityContent) {
        this.qualityContent = qualityContent;
    }

    public String getQualityUUID() {
        return qualityUUID;
    }

    public void setQualityUUID(String qualityUUID) {
        this.qualityUUID = qualityUUID;
    }

    private String qualityContent;
    private String qualityUUID;
}
