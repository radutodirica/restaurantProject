package com.example.foodzz_v2.foodzz_v2.dto;

import org.json.JSONObject;

public class UserDTO {
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

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(JSONObject json){
        if(json.has("username") && json.getString("username")!=null){
            setUsername(json.getString("username"));
        }
        if(json.has("password") && json.getString("password")!=null){
            setPassword(json.getString("password"));
        }
    }

    private String username;
    private String password;
}
