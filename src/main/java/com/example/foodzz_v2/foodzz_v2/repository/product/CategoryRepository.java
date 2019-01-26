package com.example.foodzz_v2.foodzz_v2.repository.product;

import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
    Category findByName(String name);
    Category findById(Long id);
    Category findByCategoryUUID(String categoryUUID);

    Category findByEstablishmentUUIDAndName(String establishmentUUID, String name);
}
