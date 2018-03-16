package com.example.foodzz_v2.foodzz_v2.security.service;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.User;
import com.example.foodzz_v2.foodzz_v2.security.repository.UserRepository;
import com.example.foodzz_v2.foodzz_v2.security.service.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserService implements UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public DatabaseUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean saveUsername(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        return true;
    }
}
