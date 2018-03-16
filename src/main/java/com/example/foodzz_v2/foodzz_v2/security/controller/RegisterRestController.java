package com.example.foodzz_v2.foodzz_v2.security.controller;

import com.example.foodzz_v2.foodzz_v2.dto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.security.service.DatabaseUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class RegisterRestController {

    @Autowired
    private DatabaseUserService databaseUserService;

    @RequestMapping(value="/auth/register", method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println("REQUEST: " +collect);

        UserDTO userDTO = new UserDTO(new JSONObject(collect));

        databaseUserService.saveUsername(userDTO);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .body("{\"status\":\"2\"}");
    }

}
