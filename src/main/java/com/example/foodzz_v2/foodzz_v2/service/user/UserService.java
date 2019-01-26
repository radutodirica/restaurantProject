package com.example.foodzz_v2.foodzz_v2.service.user;

import com.example.foodzz_v2.foodzz_v2.dto.userdto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.user.User;

import javax.persistence.PersistenceException;

public interface UserService {
    public User getByUsername(String username);
    public User saveUsername(UserDTO userDTO);
    public void updateUser(UserDTO userDTO) throws PersistenceException;
    public void enableUser(User user);
}
