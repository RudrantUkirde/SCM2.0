package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {


    @Autowired
    private UserService userService;

    private Logger logger=LoggerFactory.getLogger(RootController.class);

     @ModelAttribute
    public void addLoggedinUser(Model model,Authentication authentication){

        if(authentication==null){
            return;
        }

        System.out.println("add Logged in User to model");

        String userName=Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(userName);

        System.out.println(user);

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser",user);


    }

}
