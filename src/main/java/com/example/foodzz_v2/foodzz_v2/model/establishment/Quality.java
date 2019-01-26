package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTQUALITY")
public class Quality {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "quality_uuid")
    @NotNull
    private String qualityUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment qualityEstablishment;

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

    public String getQualityUUID() {
        return qualityUUID;
    }

    public void setQualityUUID(String qualityUUID) {
        this.qualityUUID = qualityUUID;
    }

    public Establishment getQualityEstablishment() {
        return qualityEstablishment;
    }

    public void setQualityEstablishment(Establishment qualityEstablishment) {
        this.qualityEstablishment = qualityEstablishment;
    }
}
