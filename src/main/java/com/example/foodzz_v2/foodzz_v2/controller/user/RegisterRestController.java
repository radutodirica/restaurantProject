package com.example.foodzz_v2.foodzz_v2.controller.user;

import com.example.foodzz_v2.foodzz_v2.dto.userdto.UserDTO;
import com.example.foodzz_v2.foodzz_v2.model.user.VerificationToken;
import com.example.foodzz_v2.foodzz_v2.service.user.VerificationTokenService;
import com.example.foodzz_v2.foodzz_v2.service.userImpl.UserServiceImpl;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class RegisterRestController {

    @Autowired
    Messages messages;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @RequestMapping(value="${route.register}", method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> registerUser(HttpServletRequest request) throws IOException{

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println("REQUEST: " +collect);

        UserDTO userDTO = new UserDTO(collect);

        StatusObject statusObject = new StatusObject();

        if(userServiceImpl.getByUsername(userDTO.getUserDTO().getUsername())!=null){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.user.alreadyTaken"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else{
            try{
                userServiceImpl.saveUsername(userDTO);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.user.created"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }catch (Exception e){
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<StatusObject> confirmRegistration(@RequestParam("token") String token){

        StatusObject statusObject = new StatusObject();
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        Date today = Calendar.getInstance().getTime();

        if(verificationToken == null){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.token.incorrect.confirmationLink"));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else if(verificationToken.getExpirationDate().before(today))
        {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.token.expired.confirmationLink"));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
        else
        {
            userServiceImpl.enableUser(verificationToken.getUser());
            verificationTokenService.deleteVerificationToken(token);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.error.user.enabled"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

}
