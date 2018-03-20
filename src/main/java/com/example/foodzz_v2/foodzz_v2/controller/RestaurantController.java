package com.example.foodzz_v2.foodzz_v2.controller;

import com.example.foodzz_v2.foodzz_v2.dto.RestaurantDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.model.Restaurant;
import com.example.foodzz_v2.foodzz_v2.service.RestaurantService;
import com.example.foodzz_v2.foodzz_v2.service.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestaurantController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantServices;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.restaurants.listrestaurants}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<List<Restaurant>> getRestaurants(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);

        List<Restaurant>restaurants = userService.getByUsername(jwtTokenUtil.getUsernameFromToken(token)).getRestaurantList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(restaurants);
    }

    @RequestMapping(value = "${route.restaurants.ceraterestaurants}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> saveRestaurant(HttpServletRequest request) throws IOException {
        StatusObject statusObject = new StatusObject();
        //String token = request.getHeader(tokenHeader).substring(7);

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        RestaurantDTO restaurantDTO = new RestaurantDTO(collect);

        if(restaurantServices.getByName(restaurantDTO.getRestaurantDTO().getName())!=null){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.restaurant.alreadyTaken"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }else{
            restaurantServices.saveRestaurant(restaurantDTO);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.restaurant.created"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }



    }

}
