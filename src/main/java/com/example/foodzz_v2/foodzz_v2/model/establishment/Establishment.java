package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ESTABLISHMENT")
public class Establishment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "description", length = 1000)
    @NotNull
    @Size(min = 4, max = 1000)
    private String description;

    @Column(name = "cuisine", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String cuisine;

    @Column(name = "latitude")
    @NotNull
    private Double latitude;

    @Column(name = "longitude")
    @NotNull
    private Double longitude;

    @Column(name = "establishment_uuid")
    @NotNull
    private String establishmentUUID;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "county")
    @NotNull
    private String county;

    @Column(name = "country")
    @NotNull
    private String country;

    @OneToMany(mappedBy="establishment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Category> categoryList;

    @ManyToMany(mappedBy = "establishments")
    @NotNull
    private List<User> users;

    @OneToMany(mappedBy = "establishment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "featuresEstablishment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Feature> features;

    @ManyToMany(mappedBy = "qualityEstablishment")
    private Set<Quality> establishmentQualities;

    @OneToMany(mappedBy = "establishment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Raiting> raitings;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEstablishmentUUID() {
        return establishmentUUID;
    }

    public void setEstablishmentUUID(String establishmentUUID) {
        this.establishmentUUID = establishmentUUID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Set<Quality> getEstablishmentQualities() {
        return establishmentQualities;
    }

    public void setEstablishmentQualities(Set<Quality> establishmentQualities) {
        this.establishmentQualities = establishmentQualities;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Raiting> getRaitings() {
        return raitings;
    }

    public void setRaitings(List<Raiting> raitings) {
        this.raitings = raitings;
    }
}
