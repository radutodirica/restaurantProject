package com.example.foodzz_v2.foodzz_v2.repository.product;

import com.example.foodzz_v2.foodzz_v2.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findByProductUUID(String productUUID);
    Product findByCategoryUUIDAndName(String categoryUUID, String productName);
}
