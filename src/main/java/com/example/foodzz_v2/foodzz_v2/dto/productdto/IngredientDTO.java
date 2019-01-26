package com.example.foodzz_v2.foodzz_v2.dto.productdto;


public class IngredientDTO {

    private String name;
    private Double quantity;
    private String ingredientUUID;
    private String productUUID;

    public IngredientDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getIngredientUUID() {
        return ingredientUUID;
    }

    public void setIngredientUUID(String ingredientUUID) {
        this.ingredientUUID = ingredientUUID;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }
}
