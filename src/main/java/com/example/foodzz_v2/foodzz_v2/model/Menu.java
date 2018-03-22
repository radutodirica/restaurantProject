package com.example.foodzz_v2.foodzz_v2.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "MENU")
public class Menu {

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<SubMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SubMenu> menuList) {
        this.menuList = menuList;
    }

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MENU_SUBMENU_ITEMS",
            joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SUBMENU_ID", referencedColumnName = "ID")})
    private List<SubMenu> menuList;
}
