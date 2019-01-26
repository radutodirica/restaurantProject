package com.example.foodzz_v2.foodzz_v2.repository.establishment;

import com.example.foodzz_v2.foodzz_v2.model.establishment.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long>{
    List<Meal> findAllBy(String establishmentUUID);
    Meal findByMealUUID(String mealUUID);
//    Meal findByEstablishmentMealUUID(String establishmentMealUUID);
}
