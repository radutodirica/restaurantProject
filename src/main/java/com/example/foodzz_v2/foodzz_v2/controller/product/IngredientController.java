package com.example.foodzz_v2.foodzz_v2.controller.product;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.IngredientDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.product.IngredientService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.IngredientValidator;
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
import java.util.List;

@RestController
public class IngredientController  {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.ingredient.getingredients}/{subCategoryUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getSubCategories(@PathVariable("subCategoryUUID") String subCategoryUUID){
        StatusObject statusObject = new StatusObject();

        List<IngredientDTO> ingredientDTOS = ingredientService.getAllIngredients(subCategoryUUID);

        if(ingredientDTOS != null && ingredientDTOS.size()>0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(ingredientDTOS);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.ingredients.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.ingredient.getingredient}/{ingredientUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> getIngredient(@PathVariable("ingredientUUID") String ingredientUUID){

        StatusObject statusObject = new StatusObject();
        IngredientDTO ingredientDTO = ObjectMapperUtils.map(ingredientService.getByUUID(ingredientUUID), IngredientDTO.class);

        if(ingredientDTO != null){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(ingredientDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.ingredient.notfound"));
            statusObject.setGenericResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }

    }

    @RequestMapping(value = "${route.ingredient.createingredient}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createIngredient(HttpServletRequest request
            , @Valid @RequestBody IngredientDTO ingredientDTO, BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        IngredientValidator ingredientValidator = new IngredientValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        ingredientValidator.validate(ingredientDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                ingredientService.createIngredient(ingredientDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.ingredient.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.ingredient.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (DefinedEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.ingredient.already.defined"));
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

    @RequestMapping(value = "${route.ingredient.updateingredient}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateIngredient(HttpServletRequest request
            , @Valid @RequestBody IngredientDTO ingredientDTO, BindingResult bindingResult) throws IOException {

        StatusObject statusObject = new StatusObject();
        IngredientValidator ingredientValidator = new IngredientValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        ingredientValidator.validate(ingredientDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                ingredientService.updateIngredient(ingredientDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.ingredient.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.ingredient.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.ingredient.update"));
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

    @RequestMapping(value = "${route.ingredient.deleteingredient}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> deleteIngredient(HttpServletRequest request,
                                                  @Valid @RequestBody IngredientDTO ingredientDTO){

        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        try{
            ingredientService.deleteIngredient(ingredientDTO, username);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.ingredient.deleted"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (UserRightsException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.ingredient.insuficient.rights"));
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (MissingEntityException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.ingredient.delete"));
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
