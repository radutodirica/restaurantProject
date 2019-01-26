package com.example.foodzz_v2.foodzz_v2.controller.product;

import com.example.foodzz_v2.foodzz_v2.dto.productdto.CategoryDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.product.CategoryService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.ObjectMapperUtils;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.CategoryValidator;
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
public class CategoryController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.category.getcategories}/{establishmentUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getCategories(@PathVariable("establishmentUUID") String establishmentUUID) {

        StatusObject statusObject = new StatusObject();
        List<CategoryDTO> categories = categoryService.findAllCategories(establishmentUUID);

            if(categories != null && categories.size()>0){
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.generalinfo"));
                statusObject.setGenericListResponse(categories);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } else {
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.categories.notfound"));
                statusObject.setGenericListResponse(null);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
    }

    @RequestMapping(value = "${route.category.getcategory}/{categoryUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getCategory(@PathVariable("categoryUUID") String categoryUUID){
        StatusObject statusObject = new StatusObject();
        CategoryDTO categoryDTO = ObjectMapperUtils.map(categoryService.getByUUID(categoryUUID), CategoryDTO.class);

        if(categoryDTO != null){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(categoryDTO);
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.category.notfound"));
            statusObject.setGenericResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.category.createcategory}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createCategory(HttpServletRequest request
            , @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult)throws IOException{

        StatusObject statusObject = new StatusObject();
        CategoryValidator categoryValidator = new CategoryValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        categoryValidator.validate(categoryDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        try {
            categoryService.createCategory(categoryDTO, username);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.category.created"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (UserRightsException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.categories.insuficient.rights"));
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (DefinedEntityException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.category.already.defined"));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
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

    @RequestMapping(value = "${route.category.updatecategory}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateCategory(HttpServletRequest request
            , @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult)throws IOException{

        StatusObject statusObject = new StatusObject();
        CategoryValidator categoryValidator = new CategoryValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        categoryValidator.validate(categoryDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }

        try {
            categoryService.updateCategory(categoryDTO, username);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.category.saved"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (UserRightsException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.categories.insuficient.rights"));
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (MissingEntityException e){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.category.update"));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch(Exception e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.generalerror"));
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.category.deletecategory}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> deleteCategory(HttpServletRequest request,
                                                @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {

        StatusObject statusObject = new StatusObject();
        CategoryValidator categoryValidator = new CategoryValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        categoryValidator.validate(categoryDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                categoryService.deleteCategory(categoryDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.category.deleted"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.categories.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.category.delete"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (Exception e) {
                statusObject.setStatus(1);
                statusObject.setMessage("text.error.generalerror");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }


}
