package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.RaitingDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.establishment.RaitingService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.DefinedEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.validator.RaitingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RaitingController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    RaitingService raitingService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.establishment.getmyestablishmentraiting}/{establishmentUUID}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> getMyEstablishmentRaiting(HttpServletRequest request,
                                                           @PathVariable("establishmentUUID") String establishmentUUID){

        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        RaitingDTO raitingDTO = raitingService.findMyRaitingByEstablishment(establishmentUUID, username);
        if(raitingDTO != null)
        {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(raitingDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else
        {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.raiting.notfound"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.addestablishmentraiting}", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> addRaiting(HttpServletRequest request, @Valid @RequestBody RaitingDTO raitingDTO,
                                            BindingResult bindingResult)
    {

        StatusObject statusObject = new StatusObject();
        RaitingValidator raitingValidator = new RaitingValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        raitingValidator.validate(raitingDTO, bindingResult);

        if(bindingResult.hasErrors())
        {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else
        {
            try {
                raitingService.addRaiting(raitingDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.raiting.added"));
                statusObject.setGenericResponse(raitingDTO);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (DefinedEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.raiting.already.defined"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.establishment.editestablishmentraiting)", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> editRaiting(HttpServletRequest request, @Valid @RequestBody RaitingDTO raitingDTO,
                                            BindingResult bindingResult)
    {

        StatusObject statusObject = new StatusObject();
        RaitingValidator raitingValidator = new RaitingValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        raitingValidator.validate(raitingDTO, bindingResult);

        if(bindingResult.hasErrors())
        {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else{
            try{
                raitingService.editRaiting(raitingDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.raiting.edited"));
                statusObject.setGenericResponse(raitingDTO);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.raiting.edit"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.establishment.removeestablishmentraiting)", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> removeRaiting(HttpServletRequest request, @Valid @RequestBody RaitingDTO raitingDTO,
                                             BindingResult bindingResult)
    {

        StatusObject statusObject = new StatusObject();
        RaitingValidator raitingValidator = new RaitingValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        raitingValidator.validate(raitingDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else
        {
            try {
                raitingService.removeRaiting(raitingDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.raiting.removed"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.raiting.remove"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }
}
