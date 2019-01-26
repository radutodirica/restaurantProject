package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "RAITING")
public class Raiting {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "RAITING_VALUE")
    @NotNull
    private Double raitingValue;

    @Column(name = "USER_NAME")
    @NotNull
    private String username;

    @Column(name = "ESTABLISHMENT_UUID")
    @NotNull
    private String establishmentUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment establishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRaitingValue() {
        return raitingValue;
    }

    public void setRaitingValue(Double raitingValue) {
        this.raitingValue = raitingValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEstablishmentUUID() {
        return establishmentUUID;
    }

    public void setEstablishmentUUID(String establishmentUUID) {
        this.establishmentUUID = establishmentUUID;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }
}
