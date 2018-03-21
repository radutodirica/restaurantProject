package com.example.foodzz_v2.foodzz_v2.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MenuDTO {

    public MenuDTO getMenuDTO() {
        return menuDTO;
    }

    public void setMenuDTO(MenuDTO menuDTO) {
        this.menuDTO = menuDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuDTO(String response){
        Gson gson = new Gson();
        this.menuDTO = gson.fromJson(response, MenuDTO.class);
    }

    private MenuDTO menuDTO;

    @SerializedName("name")
    private String name;
}
