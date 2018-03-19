package com.example.foodzz_v2.foodzz_v2.controller;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtUser;
import com.example.foodzz_v2.foodzz_v2.service.implementation.UserServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    Messages messages;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "${route.profile.userprofile}", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    @RequestMapping(value = "${route.profile.saveprofile}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> registerUser(HttpServletRequest request) throws IOException {

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println("REQUEST: " +collect);

        UserDTO userDTO = new UserDTO(collect);

        StatusObject statusObject = new StatusObject();

        userServiceImpl.updateUser(userDTO);
        statusObject.setStatus(2);
        statusObject.setMessage(messages.get("text.info.username.saved"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(statusObject);
    }

}
