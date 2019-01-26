package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTFEATURES")
public class Feature {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "feature_content", length = 500)
    @NotNull
    @Size(min = 4, max = 500)
    private String featureContent;

    @Column(name = "feature_uuid")
    @NotNull
    private String featureUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment featuresEstablishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureContent() {
        return featureContent;
    }

    public void setFeatureContent(String featureContent) {
        this.featureContent = featureContent;
    }

    public String getFeatureUUID() {
        return featureUUID;
    }

    public void setFeatureUUID(String featureUUID) {
        this.featureUUID = featureUUID;
    }

    public Establishment getFeaturesEstablishment() {
        return featuresEstablishment;
    }

    public void setFeaturesEstablishment(Establishment featuresEstablishment) {
        this.featuresEstablishment = featuresEstablishment;
    }
}
