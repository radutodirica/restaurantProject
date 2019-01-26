package com.example.foodzz_v2.foodzz_v2.service.productImpl;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.ProductDTO;
import com.example.foodzz_v2.foodzz_v2.model.product.Product;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.product.CategoryRepository;
import com.example.foodzz_v2.foodzz_v2.repository.product.ProductRepository;
import com.example.foodzz_v2.foodzz_v2.service.product.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    AuthorityServiceImpl authorityService;

    @Autowired
    UserService userService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getByUUID(String productUUID) {
        return productRepository.findByProductUUID(productUUID);
    }

    @Override
    public Product getByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<ProductDTO> findAllProducts(String categoryUUID) throws PersistenceException {

        return ObjectMapperUtils.mapAll(
                categoryRepository.findByCategoryUUID(categoryUUID).getProductsList(), ProductDTO.class);
    }

    @Override
    public void createProduct(ProductDTO productDTO, String username) throws PersistenceException, UserRightsException, DefinedEntityException {
        Product product = ObjectMapperUtils.map(productDTO, Product.class);
        User user = userService.getByUsername(username);

        //Check if the product is already defined
        if(productRepository.findByCategoryUUIDAndName(productDTO.getCategoryUUID(), productDTO.getName()) != null)
            throw new DefinedEntityException("This product is already defined for your category!");

        //Persist the product to DB
        if((authorityService.isUser(user) && product.getCategory().getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)) {
            product.setProductUUID(UUID.randomUUID().toString());
            product.setCategory(categoryRepository.findByCategoryUUID(productDTO.getCategoryUUID()));
            productRepository.save(product);
        }
        else
            throw new UserRightsException("This user doesn't have rights to create a product!");
    }

    @Override
    public void updateProduct(ProductDTO productDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {
        Product product = productRepository.findByProductUUID(productDTO.getProductUUID());
        User user = userService.getByUsername(username);

        //Check if the product exists
        if(product == null)
            throw new MissingEntityException("The product you're trying to update doesn't exist!");

        //Persist the changes to DB
        if((authorityService.isUser(user) && product.getCategory().getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user)) {
            product.setId(productRepository.findByProductUUID(productDTO.getProductUUID()).getId());
            productRepository.save(product);
        }
        else
            throw new UserRightsException("This user doesn't have rights to update this product!");

    }

    @Override
    public void deleteProduct(ProductDTO productDTO, String username) throws PersistenceException, MissingEntityException, UserRightsException {
        Product product = productRepository.findByProductUUID(productDTO.getProductUUID());
        User user = userService.getByUsername(username);

        //Check if the product exists
        if(product == null)
            throw new MissingEntityException("The product you're trying to delete doesn't exist!");

        //Delete the product
        if((authorityService.isUser(user) && product.getCategory().getEstablishment().getUsers().contains(user))
                || authorityService.isAdmin(user))
            productRepository.delete(product);
        else
            throw new UserRightsException("This user doesn't have rights to delete this product!");
    }
}
