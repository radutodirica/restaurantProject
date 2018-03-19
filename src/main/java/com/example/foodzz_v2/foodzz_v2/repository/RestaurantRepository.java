package com.example.foodzz_v2.foodzz_v2.repository;

import com.example.foodzz_v2.foodzz_v2.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByName(String name);
    Restaurant findById(Long id);

}
