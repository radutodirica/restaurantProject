package com.example.foodzz_v2.foodzz_v2.service.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.MealDTO;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface MealService {
    List<MealDTO> getAllEstablishmentMealsBy(String establishmentUUID);
    MealDTO getMeal (String mealUUID);
    void createEstablishmentMeal(MealDTO mealDTO, String username, String establishmentUUID) throws PersistenceException, UserRightsException;
    void updateEstablishmentMeal(MealDTO mealDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException;
    void deleteEstablishmentMeal(MealDTO mealDTO, String username) throws PersistenceException, UserRightsException;
}
