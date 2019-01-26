package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.FeatureDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.establishment.FeatureService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.FeatureValidator;
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
public class FeatureController {

    @Value("$(jwt.header)")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FeatureService featureService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.establishment.getfeatures}/{establishmentUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getFeatures(
            @PathVariable("establishmentUUID") String establishmentUUID) {

        StatusObject statusObject = new StatusObject();
        List<FeatureDTO> featureDTOS = featureService.getAllFeatureByEstablishment(establishmentUUID);

        if(featureDTOS != null && featureDTOS.size() > 0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(featureDTOS);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.features.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.getfeature}/{featureUUID}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> getFeature(
            @PathVariable("featureUUID") String featureUUID) {

        StatusObject statusObject = new StatusObject();
        FeatureDTO featureDTO = featureService.getByUUID(featureUUID);

        if(featureDTO != null) {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(featureDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.feature.notfound"));
            statusObject.setGenericResponse(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.createfeatures}/{establishmentUUID}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
        )
    public @ResponseBody
    ResponseEntity<StatusObject> createFeature(HttpServletRequest request, @PathVariable("establishmentUUID") String establishmentUUID
            , @Valid @RequestBody FeatureDTO featureDTO, BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        FeatureValidator featureValidator = new FeatureValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        featureValidator.validate(featureDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                featureService.createFeature(featureDTO, username, establishmentUUID);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.feature.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.feature.insuficient.rights"));
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

    @RequestMapping(value = "${route.establishment.updatefeatures}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> updateFeature(HttpServletRequest request
            , @Valid @RequestBody FeatureDTO featureDTO, BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        FeatureValidator featureValidator = new FeatureValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        featureValidator.validate(featureDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                featureService.updateFeature(featureDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.feature.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.feature.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.feature.update"));
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

    @RequestMapping(value = "${route.establishment.deletefeatures}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> deleteFeature(HttpServletRequest request
            , @Valid @RequestBody FeatureDTO featureDTO, BindingResult bindingResult) {

        StatusObject statusObject = new StatusObject();
        FeatureValidator featureValidator = new FeatureValidator();
        String token  = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        featureValidator.validate(featureDTO, bindingResult);

        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                featureService.deleteFeature(featureDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.feature.deleted"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.feature.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.feature.delete"));
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
