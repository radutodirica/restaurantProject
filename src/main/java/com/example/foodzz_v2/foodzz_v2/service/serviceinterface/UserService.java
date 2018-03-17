package com.example.foodzz_v2.foodzz_v2.service.serviceinterface;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.User;

public interface UserService {
    public User getByUsername(String username);
    public boolean  saveUsername(UserDTO userDTO);
}
