package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.persistance.User;

import java.util.List;

public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

}
