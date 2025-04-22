package com.practice.journalApp.controller;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateEntry(@RequestBody User updateUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        user.setUserName(updateUser.getUserName());
        user.setPassword(updateUser.getPassword());
        user.setRoles(updateUser.getRoles());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
