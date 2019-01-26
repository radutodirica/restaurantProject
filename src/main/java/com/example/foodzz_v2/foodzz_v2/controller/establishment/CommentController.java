package com.example.foodzz_v2.foodzz_v2.controller.establishment;

import com.example.foodzz_v2.foodzz_v2.dto.establishmentdto.CommentDTO;
import com.example.foodzz_v2.foodzz_v2.jwt.JwtTokenUtil;
import com.example.foodzz_v2.foodzz_v2.service.establishment.CommentService;
import com.example.foodzz_v2.foodzz_v2.service.establishment.EstablishmentService;
import com.example.foodzz_v2.foodzz_v2.service.user.UserService;
import com.example.foodzz_v2.foodzz_v2.util.Messages;
import com.example.foodzz_v2.foodzz_v2.util.StatusObject;
import com.example.foodzz_v2.foodzz_v2.util.exception.MissingEntityException;
import com.example.foodzz_v2.foodzz_v2.util.exception.UserRightsException;
import com.example.foodzz_v2.foodzz_v2.validator.CommentValidator;
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
public class CommentController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private EstablishmentService establishmentServices;

    @Autowired
    private CommentService commentService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.establishment.getcomments}/{establishmentUUID}", method = RequestMethod.GET ,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> getComments(
            @PathVariable("establishmentUUID") String establishmentUUID) {
        StatusObject statusObject = new StatusObject();

        List<CommentDTO> commentDTOList = commentService.getAllEstablishmentCommentsBy(establishmentUUID);

        if(commentDTOList != null && commentDTOList.size() > 0){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericListResponse(commentDTOList);
            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.comments.notfound"));
            statusObject.setGenericListResponse(null);
            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.getcomment}/{commentUUID}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> getComment(@PathVariable("commentUUID") String commentUUID){

        StatusObject statusObject = new StatusObject();
        CommentDTO commentDTO = commentService.getComment(commentUUID);

        if(commentDTO != null){
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.generalinfo"));
            statusObject.setGenericResponse(commentDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.comment.notfound"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

    @RequestMapping(value = "${route.establishment.createcommment}/{establishmentUUID}", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<StatusObject> createComment(HttpServletRequest request, @Valid @RequestBody CommentDTO commentDTO,
                                               BindingResult bindingResult, @PathVariable("establishmentUUID") String establishmentUUID){

        StatusObject statusObject = new StatusObject();
        CommentValidator commentValidator = new CommentValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        commentValidator.validate(commentDTO, bindingResult);
        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try {
                commentService.createEstablishmentComment(commentDTO, establishmentUUID, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.comment.created"));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.comment.insuficient.rights"));
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

    @RequestMapping(value = "${route.establishment.updatecomment}", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateComment(HttpServletRequest request, @Valid @RequestBody CommentDTO commentDTO,
                                               BindingResult bindingResult){

        StatusObject statusObject = new StatusObject();
        CommentValidator commentValidator = new CommentValidator();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        commentValidator.validate(commentDTO, bindingResult);
        if(bindingResult.hasErrors()){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get(bindingResult.getAllErrors().get(0).getCode()));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } else {
            try{
                commentService.updateEstablishmentComment(commentDTO, username);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.comment.saved"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (UserRightsException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.comment.insuficient.rights"));
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (MissingEntityException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.comment.update"));
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (Exception e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerrror"));
                return  ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.establishment.deletecomment}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> deleteComment(HttpServletRequest request, @RequestBody CommentDTO commentDTO){
        StatusObject statusObject = new StatusObject();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        try{
            commentService.deleteEstablishmentComment(commentDTO, username);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.comment.deleted"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (UserRightsException e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.comment.insuficient.rights"));
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (MissingEntityException e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.comment.delete"));
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
