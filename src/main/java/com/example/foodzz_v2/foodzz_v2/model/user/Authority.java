package com.example.foodzz_v2.foodzz_v2.model.user;

import com.example.foodzz_v2.foodzz_v2.util.AuthorityName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 50)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "authority")
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}