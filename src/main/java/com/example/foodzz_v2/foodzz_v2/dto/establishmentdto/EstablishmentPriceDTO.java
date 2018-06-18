package com.example.foodzz_v2.foodzz_v2.dto.establishmentdto;

public class EstablishmentPriceDTO {

    public EstablishmentPriceDTO(){}

    public String getPriceContent() {
        return priceContent;
    }

    public void setPriceContent(String priceContent) {
        this.priceContent = priceContent;
    }

    public String getPriceUUID() {
        return priceUUID;
    }

    public void setPriceUUID(String priceUUID) {
        this.priceUUID = priceUUID;
    }

    private String priceContent;
    private String priceUUID;
}
