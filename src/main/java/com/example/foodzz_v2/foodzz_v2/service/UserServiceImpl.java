package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.User;
import com.example.foodzz_v2.foodzz_v2.repository.UserRepository;
import com.example.foodzz_v2.foodzz_v2.service.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
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
    public User saveUsername(UserDTO userDTO){

        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();

        userDTO = userDTO.getUserDTO();

        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(false);

        this.userRepository.save(user);

        return user;
    }

    @Override
    public void updateUser(UserDTO userDTO) throws PersistenceException {

        userDTO = userDTO.getUserDTO();

        long userId;

        userId = this.userRepository.findByUsername(userDTO.getUsername()).getId();

        this.userRepository.saveUserData(userId,userDTO.getFirstName(),userDTO.getLastName(),userDTO.getEmail());
    }
}
