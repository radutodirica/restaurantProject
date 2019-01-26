package com.example.foodzz_v2.foodzz_v2.model.product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    @Column(name = "quantity", length = 50)
    @NotNull
    private Double quantity;

    @Column(name = "ingredient_uuid")
    @NotNull
    private String ingredientUUID;

    @Column(name = "product_uuid")
    @NotNull
    private String productUUID;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }
}
