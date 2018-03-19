package com.example.foodzz_v2.foodzz_v2.service;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.Restaurant;
import com.example.foodzz_v2.foodzz_v2.model.User;

import javax.persistence.PersistenceException;
import java.util.List;

public interface UserService {
    public User getByUsername(String username);
    public User saveUsername(UserDTO userDTO);
    public void updateUser(UserDTO userDTO) throws PersistenceException;
}
