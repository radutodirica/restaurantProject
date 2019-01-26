package com.example.foodzz_v2.foodzz_v2.model.product;


import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50)
    @NotNull
    @Size(max = 50)
    private String name;

    @Column(name = "category_uuid")
    @NotNull
    private String categoryUUID;

    @Column(name = "establishment_uuid")
    @NotNull
    private String establishmentUUID;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> productsList;

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    private Establishment establishment;

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

    public String getCategoryUUID() {
        return categoryUUID;
    }

    public void setCategoryUUID(String categoryUUID) {
        this.categoryUUID = categoryUUID;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public String getEstablishmentUUID() {
        return establishmentUUID;
    }

    public void setEstablishmentUUID(String establishmentUUID) {
        this.establishmentUUID = establishmentUUID;
    }
}
