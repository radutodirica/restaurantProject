package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTMEAL")
public class Meal {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "meal_content", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String mealContent;

    @Column(name = "meal_uuid")
    @NotNull
    private String mealUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment mealEstablishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Establishment getMealEstablishment() {
        return mealEstablishment;
    }

    public void setMealEstablishment(Establishment mealEstablishment) {
        this.mealEstablishment = mealEstablishment;
    }
}
