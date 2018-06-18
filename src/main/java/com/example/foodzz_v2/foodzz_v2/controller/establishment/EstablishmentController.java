package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.RestaurantFilterObject;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.model.User;
import com.example.foodzz_v2.foodzz_v2.service.EstablishmentService;
import com.example.foodzz_v2.foodzz_v2.service.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.validator.EstablishmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class EstablishmentController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private EstablishmentService establishmentServices;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.restaurants.listrestaurants}/{page}/{count}/{city}/{county}/{country}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getRestaurants(
            @PathVariable int page,
            @PathVariable("county") String county,
            @PathVariable("country") String country,
            @PathVariable("count") int count,
            @PathVariable("city") String city) {
        StatusObject statusObject = new StatusObject();

        List<EstablishmentDTO> establishmentDTOS = establishmentServices.getAllEstablishmentsBy(new RestaurantFilterObject(page, count, county, country, city));

        if(establishmentDTOS != null && establishmentDTOS.size()>0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(establishmentDTOS);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.articles.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.restaurants.ceraterestaurant}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createRestaurant(HttpServletRequest request
            , @Valid @RequestBody EstablishmentDTO establishmentDTO, BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        EstablishmentValidator establishmentValidator = new EstablishmentValidator();
        String token = request.getHeader(tokenHeader).substring(7);

        establishmentValidator.validate(establishmentDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                establishmentServices.createEstablishment(establishmentDTO, userService.getByUsername(jwtTokenUtil.getUsernameFromToken(token)));
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.restaurant.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }catch (PersistenceException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.restaurants.updaterestaurant}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateRestaurant(HttpServletRequest request
            , @Valid @RequestBody EstablishmentDTO establishmentDTO, BindingResult bindingResult) throws IOException {
        StatusObject statusObject = new StatusObject();
        EstablishmentValidator establishmentValidator = new EstablishmentValidator();
        String token = request.getHeader(tokenHeader).substring(7);

        establishmentValidator.validate(establishmentDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                establishmentServices.updateEstablishment(establishmentDTO, userService.getByUsername(jwtTokenUtil.getUsernameFromToken(token)));
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.restaurant.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }catch (PersistenceException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }
}
