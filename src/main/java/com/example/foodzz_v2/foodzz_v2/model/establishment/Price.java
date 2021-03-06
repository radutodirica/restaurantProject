package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTPRICE")
public class Price {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "price_range_uuid")
    @NotNull
    private String priceRangeUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment priceEstablishment;

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

    public String getPriceRangeUUID() {
        return priceRangeUUID;
    }

    public void setPriceRangeUUID(String priceRangeUUID) {
        this.priceRangeUUID = priceRangeUUID;
    }

    public Establishment getPriceEstablishment() {
        return priceEstablishment;
    }

    public void setPriceEstablishment(Establishment priceEstablishment) {
        this.priceEstablishment = priceEstablishment;
    }
}
