package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){

        return "redirect:/login";
    }

    //Home routing
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "TechSoft");
        model.addAttribute("Email", "TechSoft@gmail.com");
        return "home";
    }


    //About Routing
    @RequestMapping("/about")
    public String about(){
        return "About";
    }

    //Services Routing
    // @RequestMapping("/services")
    // public String services(){
    //     return "Services";
    // }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/login")
    public String postLogin() {        
        return "Login";
    }
    

    @GetMapping("/signup")
    public String signup(Model model){

        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);

        return "Signup";
    }

    @GetMapping("/contact")
    public String contact() {
        return "Contact";
    }

    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult,HttpSession session) {

        //Validate the User

        if(bindingResult.hasErrors()){
            return "Signup";
        }

        User user =new User();

        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://srcwap.com/wp-content/uploads/2022/08/blank-profile-picture-hd-images-photo.jpg");

        User savedUser=userService.saveUser(user);

        System.out.println("User saved:"+savedUser);

        //message after successfully completing the signup

       Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message",message);

        
        return "redirect:/signup";
    }
    


    



}
