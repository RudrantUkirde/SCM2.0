package com.scm.controller;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger=org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @ModelAttribute
    public void addLoggedinUser(Model model,Authentication authentication){

        System.out.println("add Logged in User to model");

        String userName=Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(userName);

        model.addAttribute("loggedInUser",user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

    }


    //user dashboard
    @RequestMapping(value ="/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";
    }

    @RequestMapping(value ="/dashboard", method=RequestMethod.POST)
    public String POSTuserDashboard() {
        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value ="/profile", method=RequestMethod.GET)
    public String userProfile(Model model,Authentication authentication){

        return "user/profile";
    }

    @RequestMapping(value ="/profile", method=RequestMethod.POST)
    public String POSTuserProfile(Model model, Authentication authentication) {
        return "user/profile";
    }
    


}
