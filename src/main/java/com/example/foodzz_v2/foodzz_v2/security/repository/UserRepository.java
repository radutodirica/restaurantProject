package com.example.foodzz_v2.foodzz_v2.security.repository;


import com.example.foodzz_v2.foodzz_v2.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
