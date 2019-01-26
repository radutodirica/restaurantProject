package com.example.foodzz_v2.foodzz_v2.service.userImpl;

import com.example.foodzz_v2.foodzz_v2.jwt.JwtUserFactory;
import com.example.foodzz_v2.foodzz_v2.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
//            user.setEnabled(true);
            return JwtUserFactory.create(user);
        }
    }
}
