package com.example.foodzz_v2.foodzz_v2.model.establishment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTMEAL")
public class EstablishmentMeal {

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

    public String getMealUUID() {
        return mealUUID;
    }

    public void setMealUUID(String mealUUID) {
        this.mealUUID = mealUUID;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "meal_uuid")
    @NotNull
    private String mealUUID;
}
