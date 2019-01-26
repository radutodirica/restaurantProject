package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.MealDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.establishment.MealService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.MealValidator;
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
import java.util.List;


@RestController
public class MealController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.establishment.getmeals}/{establishmentUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getMeals(@PathVariable("establishmentUUID") String establishmentUUID) {
        StatusObject statusObject = new StatusObject();

        List<MealDTO> mealDTOList = mealService.getAllEstablishmentMealsBy(establishmentUUID);

        if (mealDTOList != null && mealDTOList.size() > 0) {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(mealDTOList);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.meals.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.getmeal}/{mealUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getMeal(@PathVariable("mealUUID") String mealUUID) {
        StatusObject statusObject = new StatusObject();

        MealDTO mealDTO  = mealService.getMeal(mealUUID);
        if(mealDTO != null){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(mealDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.meal.notfound"));
            statusObject.setGenericResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }

    }

    @RequestMapping(value = "${route.establishment.createmeal}/{establishmentUUID}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createMeal(HttpServletRequest request, @Valid @RequestBody MealDTO mealDTO,
                                            @PathVariable("establishmentUUID") String establishmentUUID, BindingResult bindingResult) {

        StatusObject statusObject = new StatusObject();
        MealValidator mealValidator = new MealValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        mealValidator.validate(mealDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                mealService.createEstablishmentMeal(mealDTO, username, establishmentUUID);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.meal.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.meal.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (Exception e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }

        }

    }

    @RequestMapping(value = "${route.establishment.updatemeal}", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    public @ResponseBody
    ResponseEntity<StatusObject> updateMeal(HttpServletRequest request, @Valid @RequestBody MealDTO mealDTO,
                                            BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        MealValidator mealValidator = new MealValidator();
        String username = jwtTokenUtil.getUsernameFromToken(token);

        mealValidator.validate(mealDTO, bindingResult);
        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                mealService.updateEstablishmentMeal(mealDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.meal.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.meal.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.meal.update"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (Exception e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.establishment.deletemeal}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> deleteMeal(HttpServletRequest request, @RequestBody MealDTO mealDTO){

        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        try{
            mealService.deleteEstablishmentMeal(mealDTO, username);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.meal.deleted"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (UserRightsException e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.meal.insuficient.rights"));
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (Exception e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.generalerror"));
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }
}
