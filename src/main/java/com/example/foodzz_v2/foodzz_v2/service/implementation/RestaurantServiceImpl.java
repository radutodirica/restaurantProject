package com.example.foodzz_v2.foodzz_v2.service.implementation;

import com.example.foodzz_v2.foodzz_v2.dto.RestaurantDTO;
import com.example.foodzz_v2.foodzz_v2.model.Restaurant;
import com.example.foodzz_v2.foodzz_v2.repository.RestaurantRepository;
import com.example.foodzz_v2.foodzz_v2.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant getById(Long id) {
        return this.restaurantRepository.findById(id);
    }

    @Override
    public Restaurant getByName(String name) {
        return this.restaurantRepository.findByName(name);
    }

    @Override
    public Restaurant createRestaurant(RestaurantDTO restaurantDTO) throws PersistenceException{

        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDTO.getRestaurantDTO().getName());
        restaurant.setLatitude(restaurantDTO.getRestaurantDTO().getLatitude());
        restaurant.setLongitude(restaurantDTO.getRestaurantDTO().getLongitude());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurant(RestaurantDTO restaurantDTO) throws PersistenceException {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDTO.getRestaurantDTO().getName());
        restaurant.setLatitude(restaurantDTO.getRestaurantDTO().getLatitude());
        restaurant.setLongitude(restaurantDTO.getRestaurantDTO().getLongitude());
    }
}
