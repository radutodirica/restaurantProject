package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.EstablishmentDTO;
import com.example.foodzz_v2.foodzz_v2.filter.EstablishmentFilterObject;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.establishment.EstablishmentService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.EstablishmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
    private JavaMailSender sender;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.establishment.getestablishments}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getEstablishments(
            HttpServletRequest request) {
        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        List<EstablishmentDTO> establishmentDTOS = establishmentServices.getAllEstablishments(username);

        if(establishmentDTOS != null && establishmentDTOS.size() > 0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(establishmentDTOS);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.establishments.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.getmyestablishments}/{page}/{count}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getMyEstablishments(
            HttpServletRequest request,
            @PathVariable("page") int page,
            @PathVariable("count") int count){

        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        List<EstablishmentDTO> establishmentDTOS = establishmentServices.getAllEstablishmentsByUser(new EstablishmentFilterObject(page, count), username);

        if(establishmentDTOS != null && establishmentDTOS.size() > 0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(establishmentDTOS);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.my.establishments.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.getestablishment}/{establishmentUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getEstablishment(@PathVariable("establishmentUUID") String establishmentUUID){

        StatusObject statusObject = new StatusObject();
        EstablishmentDTO establishmentDTO = establishmentServices.getByUUID(establishmentUUID);

        if(establishmentDTO != null){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(establishmentDTO);
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.establishment.notfound"));
            statusObject.setGenericResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }

    }

    @RequestMapping(value = "${route.establishment.createestablishment}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createEstablishment(HttpServletRequest request
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
                establishmentServices.createEstablishment(establishmentDTO, jwtTokenUtil.getUsernameFromToken(token));
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.establishment.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.establishment.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (DefinedEntityException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.establishment.already.defined"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
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

    @RequestMapping(value = "${route.establishment.updateestablishment}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateEstablishment(HttpServletRequest request
            , @Valid @RequestBody EstablishmentDTO establishmentDTO, BindingResult bindingResult) throws IOException {
        StatusObject statusObject = new StatusObject();
        EstablishmentValidator establishmentValidator = new EstablishmentValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        establishmentValidator.validate(establishmentDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                establishmentServices.updateEstablishment(establishmentDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.establishment.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.establishment.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.establishment.update"));
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

    @RequestMapping(value = "${route.establishment.deleteestablishment}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> deleteEstablishment(HttpServletRequest request
            , @Valid @RequestBody EstablishmentDTO establishmentDTO, BindingResult bindingResult) {

        StatusObject statusObject = new StatusObject();
        EstablishmentValidator establishmentValidator = new EstablishmentValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        establishmentValidator.validate(establishmentDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                establishmentServices.deleteEstablishment(establishmentDTO.getEstablishmentUUID(),username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.establishment.deleted"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.establishment.insuficient.rights"));
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
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }
}
