package com.example.foodzz_v2.foodzz_v2.service.implementation;


import com.example.foodzz_v2.foodzz_v2.dto.MenuDTO;
import com.example.foodzz_v2.foodzz_v2.model.Menu;
import com.example.foodzz_v2.foodzz_v2.repository.MenuRepository;
import com.example.foodzz_v2.foodzz_v2.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu findById(long id) {
        return this.menuRepository.findById(id);
    }

    @Override
    public Menu getByName(String name) {
        return this.menuRepository.findByName(name);
    }

    @Override
    public Menu createMenu(MenuDTO menuDTO) throws PersistenceException {

        Menu menu = new Menu();

        menu.setName(menuDTO.getMenuDTO().getName());

        return menu;
    }

    @Override
    public void updateMenu(MenuDTO menuDTO) throws PersistenceException{
        Menu menu = new Menu();

        menu.setName(menuDTO.getMenuDTO().getName());
    }
}
