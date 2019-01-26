package com.example.foodzz_v2.foodzz_v2.service.productImpl;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.IngredientDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Ingredient;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.product.IngredientRepository;
import com.example.foodzz_v2.foodzz_v2.repository.product.ProductRepository;
import com.example.foodzz_v2.foodzz_v2.service.product.IngredientService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.AuthorityServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    UserService userService;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ProductRepository productRepository)
    {
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Ingredient getByUUID(String ingredientUUID) {
        return ingredientRepository.findByIngredientUUID(ingredientUUID);
    }

    @Override
    public Ingredient getByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Override
    public List<IngredientDTO> getAllIngredients(String subCategoryUUID) throws PersistenceException {
        return ObjectMapperUtils.mapAll(
                productRepository.findByProductUUID(subCategoryUUID).getIngredientList(), IngredientDTO.class);
    }

    @Override
    public void createIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException {
        Ingredient ingredient = ObjectMapperUtils.map(ingredientDTO, Ingredient.class);
        User user = userService.getByUsername(username);

        //Check if the ingredient is already defined
        if(ingredientRepository.findByProductUUIDAndName(ingredientDTO.getProductUUID(), ingredientDTO.getName()) != null)
            throw new DefinedEntityException("This ingredient is already defined for your product!");

        //Persist the ingredient to DB
        if((authorityService.isUser(user) && ingredient.getProduct().getCategory().getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)){
            ingredient.setIngredientUUID(UUID.randomUUID().toString());
            ingredient.setProduct(productRepository.findByProductUUID(ingredientDTO.getProductUUID()));
            ingredientRepository.save(ingredient);
        }
        else
          throw new UserRightsException("This user doesn't have rights to create a ingredient!");

    }

    @Override
    public void updateIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {

       Ingredient ingredient = ingredientRepository.findByIngredientUUID(ingredientDTO.getIngredientUUID());
       User user = userService.getByUsername(username);

       //Check if the ingredient exists
       if(ingredient == null)
           throw new MissingEntityException("The ingredient you're trying to update doesn't exist!");

       //Persist the changes to DB
       if((authorityService.isUser(user) && ingredient.getProduct().getCategory().getEstablishment().getUsers().contains(user))
               || authorityService.isAdmin(user)) {
           ingredient.setId(ingredientRepository.findByIngredientUUID(ingredientDTO.getIngredientUUID()).getId());
           ingredientRepository.save(ingredient);
       }
       else
           throw new UserRightsException("This user doesn't have rights to update a ingredient!");
    }

    @Override
    public void deleteIngredient(IngredientDTO ingredientDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {
        Ingredient ingredient = ingredientRepository.findByIngredientUUID(ingredientDTO.getIngredientUUID());
        User user = userService.getByUsername(username);

        //Check if the ingredient exists
        if(ingredient == null)
            throw new MissingEntityException("The ingredient you're trying to delete doesn't exist!");

        //Delete the ingredient
        if((authorityService.isUser(user) && ingredient.getProduct().getCategory().getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user))
            ingredientRepository.delete(ingredient);
        else
            throw new UserRightsException("This user doesn't have rights to delete a ingredient!");
    }
}
