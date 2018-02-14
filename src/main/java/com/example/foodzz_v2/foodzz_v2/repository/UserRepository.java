package com.example.foodzz_v2.foodzz_v2.repository;


import com.example.foodzz_v2.foodzz_v2.persistance.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
