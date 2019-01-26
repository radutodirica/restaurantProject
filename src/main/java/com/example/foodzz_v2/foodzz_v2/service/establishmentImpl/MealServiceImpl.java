package com.example.foodzz_v2.foodzz_v2.service.establishmentImpl;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.MealDTO;
import com.example.foodzz_v2.foodzz_v2.model.establishment.Meal;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.MealRepository;
import com.example.foodzz_v2.foodzz_v2.service.establishment.MealService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.AuthorityServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final EstablishmentRepository establishmentRepository;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    UserService userService;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, EstablishmentRepository establishmentRepository){
        this.mealRepository = mealRepository;
        this.establishmentRepository  = establishmentRepository;
    }

    @Override
    public List<MealDTO> getAllEstablishmentMealsBy(String establishmentUUID) {
        List<Meal> meals = mealRepository.findAllBy(establishmentUUID);
        return ObjectMapperUtils.mapAll(meals, MealDTO.class);
    }

    @Override
    public MealDTO getMeal(String mealUUID) {
        Meal meal = mealRepository.findByMealUUID(mealUUID);
        return ObjectMapperUtils.map(meal, MealDTO.class);
    }

    @Override
    public void createEstablishmentMeal(MealDTO mealDTO, String username, String establishmentUUID) throws PersistenceException, UserRightsException {
        Meal meal = ObjectMapperUtils.map(mealDTO, Meal.class);
        User user = userService.getByUsername(username);

        //Persist the meal to DB
        if((authorityService.isUser(user) && meal.getMealEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)) {
            meal.setMealEstablishment(establishmentRepository.findByEstablishmentUUID(establishmentUUID));
            mealRepository.save(meal);
        }
        else
            throw new UserRightsException("This user don't have rights to create meal for this establishment!");
    }

    @Override
    public void updateEstablishmentMeal(MealDTO mealDTO, String username) throws PersistenceException, UserRightsException, MissingEntityException {
        Meal meal = mealRepository.findByMealUUID(mealDTO.getMealUUID());
        User user = userService.getByUsername(username);

        //Check if the meal exist
        if(meal == null)
            throw new MissingEntityException("The meal you're trying to update doesn't exist!");

        //Set the new content of the meal
        meal.setMealContent(mealDTO.getMealContent());

        //Persist the meal to DB
        if((authorityService.isUser(user) && meal.getMealEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)){
            mealRepository.save(meal);
        }
        else
            throw new UserRightsException("This user don't have rights to update meal for this establishment!");


    }

    @Override
    public void deleteEstablishmentMeal(MealDTO mealDTO, String username) throws PersistenceException, UserRightsException {
        Meal meal = ObjectMapperUtils.map(mealDTO, Meal.class);
        User user = userService.getByUsername(username);

        if((authorityService.isUser(user) && meal.getMealEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user))
            mealRepository.delete(meal);
        else
            throw new UserRightsException("This user don't have rights to delete meal for this establishment!");
    }
}
