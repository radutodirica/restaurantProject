package com.example.foodzz_v2.foodzz_v2.model.establishment;

import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ESTABLISHMENTCOMMENT")
public class Comment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "comment_content")
    @NotNull
    @Size(min = 4, max = 50)
    private String commentContent;

    @Column(name = "comment_uuid")
    @NotNull
    private String commentUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="establishment_id", nullable=false)
    private Establishment establishment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentUUID() {
        return commentUUID;
    }

    public void setCommentUUID(String commentUUID) {
        this.commentUUID = commentUUID;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
