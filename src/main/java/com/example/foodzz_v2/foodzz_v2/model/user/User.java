package com.example.foodzz_v2.foodzz_v2.model.user;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Establishment;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER_DATA")
public class User implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "USER_AUTHORITY",
//            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
//            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
//    private List<Authority> authorities;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_ESTABLISHMENT",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ESTABLISHMENT_ID", referencedColumnName = "ID")})
    private Set<Establishment> establishments;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Comment> comments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private VerificationToken verificationToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<Establishment> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(Set<Establishment> establishments) {
        this.establishments = establishments;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    @Override
    public boolean equals(Object obj) {
        User object = (User)obj;
        return
            this.getUsername().equals(object.getUsername()) &&
            this.getEmail().equals(object.getEmail());

    }
}