package com.example.foodzz_v2.foodzz_v2.dto.userdto;

import com.example.foodzz_v2.foodzz_v2.model.user.Authority;
import com.google.gson.Gson;

import java.util.List;

public class UserDTO{

    private UserDTO userDTO;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private String authorityName;

    public UserDTO() {
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserDTO(String response){
        Gson gson = new Gson();
        this.userDTO = gson.fromJson(response,UserDTO.class);
    }
}
