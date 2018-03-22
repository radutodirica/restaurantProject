package com.example.foodzz_v2.foodzz_v2.repository;

import com.example.foodzz_v2.foodzz_v2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.firstname = :firstName, u.lastname = :lastName, u.email = :email WHERE u.id = :userId")
    void saveUserData(
            @Param("userId")long userId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
