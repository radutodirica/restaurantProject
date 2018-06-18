package com.example.foodzz_v2.foodzz_v2.model.establishment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTFEATURES")
public class EstablishmentFeatures {

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

    public String getFeatureUUID() {
        return featureUUID;
    }

    public void setFeatureUUID(String featureUUID) {
        this.featureUUID = featureUUID;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "feature_uuid")
    @NotNull
    private String featureUUID;
}
