package com.example.foodzz_v2.foodzz_v2.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "RESTAURANT")
public class Restaurant {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "latitude")
    @NotNull
    private Double latitude;

    @Column(name = "longitude")
    @NotNull
    private Double longitude;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ID",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private Long userId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "MENU_ITEMS",
            joinColumns = {@JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "ID")})
    private List<Menu> menuList;

}
