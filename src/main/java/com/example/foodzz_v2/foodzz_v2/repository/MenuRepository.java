package com.example.foodzz_v2.foodzz_v2.repository;

import com.example.foodzz_v2.foodzz_v2.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>  {
    Menu findByName(String name);
    Menu findById(Long id);
}
