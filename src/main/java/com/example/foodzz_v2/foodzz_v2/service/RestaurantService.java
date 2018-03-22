package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.dto.RestaurantDTO;
import com.example.foodzz_v2.foodzz_v2.model.Restaurant;

import javax.persistence.PersistenceException;
import java.util.List;

public interface RestaurantService {

    public Restaurant getById(Long id);
    public Restaurant getByName(String name);
    public Restaurant createRestaurant(RestaurantDTO restaurantDTO);
    public void updateRestaurant(RestaurantDTO restaurantDTO)throws PersistenceException;

}
