package com.example.foodzz_v2.foodzz_v2.service.product;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface CategoryService {

    Category getByUUID(String categoryUUID);
    Category getByName(String name);
    List<CategoryDTO> findAllCategories(String establishmentUUID) throws PersistenceException;
    void createCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException;
    void updateCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;
    void deleteCategory(CategoryDTO categoryDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;

}
