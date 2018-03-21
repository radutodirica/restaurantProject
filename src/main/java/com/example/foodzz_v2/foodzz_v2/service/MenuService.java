package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.dto.MenuDTO;
import com.example.foodzz_v2.foodzz_v2.model.Menu;

public interface MenuService {

    public Menu findById(long id);
    public Menu getByName(String name);
    public Menu createMenu(MenuDTO menuDTO);
    public void updateMenu(MenuDTO menuDTO);

}
