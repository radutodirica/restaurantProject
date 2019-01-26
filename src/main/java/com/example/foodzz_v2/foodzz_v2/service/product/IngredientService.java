package com.example.foodzz_v2.foodzz_v2.service.product;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.IngredientDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Ingredient;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface IngredientService {

    Ingredient getByUUID(String ingredientUUID);
    Ingredient getByName(String name);
    List<IngredientDTO> getAllIngredients (String subCategoryUUID) throws PersistenceException;
    void createIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException;
    void updateIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;
    void deleteIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;
}
