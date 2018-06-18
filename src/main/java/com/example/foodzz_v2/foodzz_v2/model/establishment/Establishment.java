package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.example.foodzz_v2.foodzz_v2.model.Menu;
import com.example.foodzz_v2.foodzz_v2.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ESTABLISHMENT")
public class Establishment {

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

    public Set<Integer> getRating() {
        return rating;
    }

    public void setRating(Set<Integer> rating) {
        this.rating = rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Set<EstablishmentComment> getEstablishmentComments() {
        return establishmentComments;
    }

    public void setEstablishmentComments(Set<EstablishmentComment> establishmentComments) {
        this.establishmentComments = establishmentComments;
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

    public String getCityUUID() {
        return cityUUID;
    }

    public void setCityUUID(String cityUUID) {
        this.cityUUID = cityUUID;
    }

    public String getCountyUUID() {
        return countyUUID;
    }

    public void setCountyUUID(String countyUUID) {
        this.countyUUID = countyUUID;
    }

    public String getCountryUUID() {
        return countryUUID;
    }

    public void setCountryUUID(String countryUUID) {
        this.countryUUID = countryUUID;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Set<EstablishmentMeal> getEstablishmentMeals() {
        return establishmentMeals;
    }

    public void setEstablishmentMeals(Set<EstablishmentMeal> establishmentMeals) {
        this.establishmentMeals = establishmentMeals;
    }

    public EstablishmentPrice getEstablishmentPrice() {
        return establishmentPrice;
    }

    public void setEstablishmentPrice(EstablishmentPrice establishmentPrice) {
        this.establishmentPrice = establishmentPrice;
    }

    public Set<EstablishmentFeatures> getEstablishmentFeatures() {
        return establishmentFeatures;
    }

    public void setEstablishmentFeatures(Set<EstablishmentFeatures> establishmentFeatures) {
        this.establishmentFeatures = establishmentFeatures;
    }

    public Set<EstablishmentQuality> getEstablishmentQualities() {
        return establishmentQualities;
    }

    public void setEstablishmentQualities(Set<EstablishmentQuality> establishmentQualities) {
        this.establishmentQualities = establishmentQualities;
    }

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

    @Column(name = "cuisine", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String cuisine;

    @Column(name = "rating")
    @NotNull
    private Set<Integer> rating;

    @Column(name = "latitude")
    @NotNull
    private Double latitude;

    @OneToMany(mappedBy = "commentList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    private Set<EstablishmentComment> establishmentComments;

    @Column(name = "longitude")
    @NotNull
    private Double longitude;

    @Column(name = "establishment_uuid")
    @NotNull
    private String establishmentUUID;

    @Column(name = "city_uuid")
    @NotNull
    private String cityUUID;

    @Column(name = "county_uuid")
    @NotNull
    private String countyUUID;

    @Column(name = "country_uuid")
    @NotNull
    private String countryUUID;

    @OneToMany(mappedBy="establishment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    private List<Menu> menuList;

    @ManyToMany(mappedBy = "establishments")
    @NotNull
    private User users;

    @ManyToMany(mappedBy = "mealList")
    @NotNull
    private Set<EstablishmentMeal> establishmentMeals;

    @ManyToMany(mappedBy = "priceList")
    @NotNull
    private EstablishmentPrice establishmentPrice;

    @ManyToMany(mappedBy = "futuresList")
    @NotNull
    private Set<EstablishmentFeatures> establishmentFeatures;

    @ManyToMany(mappedBy = "qualityList")
    @NotNull
    private Set<EstablishmentQuality> establishmentQualities;
}
