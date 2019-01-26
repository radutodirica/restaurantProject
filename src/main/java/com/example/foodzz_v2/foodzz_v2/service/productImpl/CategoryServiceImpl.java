package com.example.foodzz_v2.foodzz_v2.service.productImpl;


import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.product.CategoryRepository;
import com.example.foodzz_v2.foodzz_v2.repository.establishment.EstablishmentRepository;
import com.example.foodzz_v2.foodzz_v2.service.product.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EstablishmentRepository establishmentRepository;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    UserService userService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, EstablishmentRepository establishmentRepository){
        this.categoryRepository = categoryRepository;
        this.establishmentRepository = establishmentRepository;
    }

    @Override
    public Category getByUUID(String categoryUUID) {
        return this.categoryRepository.findByCategoryUUID(categoryUUID);
    }

    @Override
    public Category getByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    @Override
    public List<CategoryDTO> findAllCategories(String establishmentUUID) throws PersistenceException {

        return ObjectMapperUtils.mapAll(
                establishmentRepository.findByEstablishmentUUID(establishmentUUID).getCategoryList(), CategoryDTO.class);
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException {
        Category category = ObjectMapperUtils.map(categoryDTO, Category.class);
        User user = userService.getByUsername(username);

        //Check if the category is already defined
        if(categoryRepository.findByEstablishmentUUIDAndName(categoryDTO.getEstablishmentUUID(), categoryDTO.getName()) != null)
            throw new DefinedEntityException("This category is already defined for this establishment!");

        //Persist the category to DB
        if((authorityService.isUser(user) && category.getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)){
            category.setCategoryUUID(UUID.randomUUID().toString());
            category.setEstablishment(establishmentRepository.findByEstablishmentUUID(categoryDTO.getEstablishmentUUID()));
            categoryRepository.save(category);
        }
        else
            throw new UserRightsException("This user doesn't have rights to update the category!");
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {
        Category category = categoryRepository.findByCategoryUUID(categoryDTO.getCategoryUUID());
        User user = userService.getByUsername(username);

        //Check if the category exists
        if(category == null)
            throw new MissingEntityException("The category you are trying to update doesn't exist!");

        //Persist the changes of the category to DB
        if((authorityService.isUser(user) && category.getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)){
            category.setId(categoryRepository.findByCategoryUUID(categoryDTO.getCategoryUUID()).getId());
            categoryRepository.save(category);
        }
        else
            throw new UserRightsException("This user doesn't have rights to update the category!");
    }

    @Override
    public void deleteCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {
        Category category = categoryRepository.findByCategoryUUID(categoryDTO.getCategoryUUID());
        User user = userService.getByUsername(username);

        //Check if the category exists
        if(category == null)
            throw new MissingEntityException("The category you are trying to delete doesn't exist!");

        //Delete the category
        if((authorityService.isUser(user) && category.getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user))
            categoryRepository.delete(category);
        else
            throw new UserRightsException("This user doesn't have rights to delete the category!");
    }
}
