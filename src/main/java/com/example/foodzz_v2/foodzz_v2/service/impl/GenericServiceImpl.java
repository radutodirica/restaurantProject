package com.example.foodzz_v2.foodzz_v2.service.impl;

import com.example.foodzz_v2.foodzz_v2.persistance.User;
import com.example.foodzz_v2.foodzz_v2.repository.UserRepository;
import com.example.foodzz_v2.foodzz_v2.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }
}
