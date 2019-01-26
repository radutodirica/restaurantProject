package com.example.foodzz_v2.foodzz_v2.service.user;

import com.example.foodzz_v2.foodzz_v2.model.user.Authority;
import com.example.foodzz_v2.foodzz_v2.model.user.User;

import java.util.List;

public interface AuthorityService {
    Authority getAuthority(String authorityName);
    boolean isUser(User user);
    boolean isAdmin(User user);
}
