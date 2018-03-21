package com.example.foodzz_v2.foodzz_v2.controller;

import com.example.foodzz_v2.foodzz_v2.dto.MenuDTO;
import com.example.foodzz_v2.foodzz_v2.model.Menu;
import com.example.foodzz_v2.foodzz_v2.service.MenuService;
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
import java.util.stream.Collectors;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    Messages messages;

    @RequestMapping(value = "${route.menu.getMenus}/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<Menu> getRestaurants(@PathVariable long id) {

        Menu menu = menuService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(menu);
    }

    @RequestMapping(value = "${route.menu.createMenu}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> createMenu(HttpServletRequest request)throws IOException{
        StatusObject statusObject = new StatusObject();

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        MenuDTO menuDTO = new MenuDTO(collect);

        if(menuService.getByName(menuDTO.getMenuDTO().getName())!=null){
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.menu.alreadyTaken"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }else {
            try {
                menuService.createMenu(menuDTO);
                statusObject.setStatus(2);
                statusObject.setMessage(messages.get("text.info.menu.created"));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            } catch (PersistenceException e) {
                statusObject.setStatus(1);
                statusObject.setMessage(messages.get("text.error.generalerror"));
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .cacheControl(CacheControl.noCache())
                        .body(statusObject);
            }
        }
    }

    @RequestMapping(value = "${route.menu.createmenu}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<StatusObject> updateMenu(HttpServletRequest request)throws IOException{
        StatusObject statusObject = new StatusObject();

        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        MenuDTO menuDTO = new MenuDTO(collect);

        try {
            menuService.updateMenu(menuDTO);
            statusObject.setStatus(2);
            statusObject.setMessage(messages.get("text.info.menu.saved"));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        } catch (PersistenceException e) {
            statusObject.setStatus(1);
            statusObject.setMessage(messages.get("text.error.generalerror"));
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .cacheControl(CacheControl.noCache())
                    .body(statusObject);
        }
    }

}
