package com.example.foodzz_v2.foodzz_v2.service.serviceinterface;

import com.example.foodzz_v2.foodzz_v2.dto.RestaurantDTO;
import com.example.foodzz_v2.foodzz_v2.model.Restaurant;

import javax.persistence.PersistenceException;

public interface RestaurantServices {

    public Restaurant getbyName();
    public Restaurant saveRestaurant(RestaurantDTO restaurantDTO);
    public void updateRestaurant(RestaurantDTO restaurantDTO)throws PersistenceException;

}
