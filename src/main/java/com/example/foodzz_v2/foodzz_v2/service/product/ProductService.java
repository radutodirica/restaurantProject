package com.example.foodzz_v2.foodzz_v2.service.product;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.ProductDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Product;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;

import javax.persistence.PersistenceException;
import java.util.List;

public interface ProductService {

    Product getByUUID(String productUUID);
    Product getByName(String name);
    List<ProductDTO> findAllProducts(String categoryUUID) throws PersistenceException;
    void createProduct(ProductDTO productDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException;
    void updateProduct(ProductDTO productDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;
    void deleteProduct(ProductDTO productDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException;
}
