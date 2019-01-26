package com.example.foodzz_v2.foodzz_v2.repository.product;

import com.example.foodzz_v2.foodzz_v2.model.product.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);
    Ingredient findByIngredientUUID(String ingredientUUID);
    Ingredient findByProductUUIDAndName(String productUUID, String ingredientName);
}
