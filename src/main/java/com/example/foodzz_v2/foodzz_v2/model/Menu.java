package com.example.foodzz_v2.foodzz_v2.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "MENU")
public class Menu {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "price", length = 50)
    @NotNull
    private Double price;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SUBMENU_ITEMS",
            joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SUBMENU_ID", referencedColumnName = "ID")})
    private List<SubMenu> menuList;
}
