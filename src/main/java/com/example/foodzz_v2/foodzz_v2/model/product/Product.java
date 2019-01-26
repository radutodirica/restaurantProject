package com.example.foodzz_v2.foodzz_v2.model.product;

import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.model.product.Ingredient;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "price", length = 50)
    @NotNull
    private Double price;

    @Column(name = "product_uuid")
    @NotNull
    private String productUUID;

    @Column(name = "category_uuid")
    @NotNull
    private String categoryUUID;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Ingredient> ingredientList;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryUUID() {
        return categoryUUID;
    }

    public void setCategoryUUID(String categoryUUID) {
        this.categoryUUID = categoryUUID;
    }
}
