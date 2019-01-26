package com.example.foodzz_v2.foodzz_v2.service.userImpl;

import com.example.foodzz_v2.foodzz_v2.model.user.Authority;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import com.example.foodzz_v2.foodzz_v2.repository.user.AuthorityRepository;
import com.example.foodzz_v2.foodzz_v2.service.user.AuthorityService;
import com.example.foodzz_v2.foodzz_v2.util.AuthorityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthority(String authorityName) {
        return authorityRepository.findByName(authorityName);
    }

    @Override
    public boolean isUser(User user) {
        return user.getAuthority().getName().equals(AuthorityName.ROLE_USER.toString());
    }

    @Override
    public boolean isAdmin(User user) {
        return user.getAuthority().getName().equals(AuthorityName.ROLE_ADMIN.toString());
    }
}
