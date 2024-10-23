package com.scm.controller;

import java.util.UUID;

import org.slf4j.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.SearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {


    private Logger logger = LoggerFactory.getLogger(ContactController.class);
    

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;


    @Autowired
    private UserService userService;


    //add contact page handler
    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm=new ContactForm();
        model.addAttribute("contactForm", contactForm);


        return "user/add_contact";
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session){

        String fileName=UUID.randomUUID().toString();

        //Validate the Form
        if(result.hasErrors()){

            result.getAllErrors().forEach(error ->{
                logger.info(error.toString());
            });

            session.setAttribute("message", Message.builder()
            .content("Please resolve the errors below!!")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        //Form data processing
        String userName=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(userName);

        //processing contact image
        logger.info("file information:{}",contactForm.getContactImage().getOriginalFilename());

        String fileURL=imageService.uploadImage(contactForm.getContactImage(),fileName);

        //Contact Form ----. contact
        Contact contact=new Contact();

            contact.setName(contactForm.getName());
            contact.setFavourite(contactForm.isFavourite());
            contact.setEmail(contactForm.getEmail());
            contact.setPhoneNumber(contactForm.getPhoneNumber());
            contact.setAddress(contactForm.getAddress());
            contact.setDescription(contactForm.getDescription());
            contact.setWebsiteLink(contactForm.getWebsiteLink());
            contact.setLinkedInLink(contactForm.getLinkedInLink());
            contact.setUser(user);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(fileName);

        //saving contact to contact table in DB
        contactService.save(contact);

        session.setAttribute("message", Message.builder()
            .content("Contact Added Successfully!!!")
            .type(MessageType.green)
            .build());
        return "redirect:/user/contacts/add";

    }


    //view all contacts API

    @RequestMapping
    public String viewContacts(
        @RequestParam(value="page",defaultValue = "0")int page,
        @RequestParam(value = "size",defaultValue = "10")int size,
        @RequestParam(value ="sortBy",defaultValue="name")String sortBy,
        @RequestParam(value="direction",defaultValue = "asc")String direction
        ,Authentication authentication,Model model){


        //load all the user contacts
        //get all contacts by user id

        String username=Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(username);

        Page<Contact> pageContact=contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("searchForm", new SearchForm());


        return "user/contacts";
    }

    //Search in All Contacts API

    @RequestMapping("/search")    
    public String searchHandler(
        @ModelAttribute SearchForm searchForm,
        @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value="page",defaultValue="0")int page,
        @RequestParam(value="sortBy",defaultValue = "name")String sortBy,
        @RequestParam(value="size",defaultValue ="asc")String direction,
        Model model,
        Authentication authentication
        
    ){

        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact=null;

        String field=searchForm.getField();
        String keyword=searchForm.getKeyword();

        if(field.equalsIgnoreCase("name")){

            pageContact=contactService.searchByName(keyword,size,page,sortBy,direction,user);

        }else if(field.equalsIgnoreCase("email")){

            pageContact=contactService.searchByEmail(keyword,size,page,sortBy,direction,user);

        }else if(field.equalsIgnoreCase("phoneNumber")){

            pageContact=contactService.searchByPhoneNumber(keyword,size,page,sortBy,direction,user);

        }

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";

    }


    //Favourite Contact API

    @RequestMapping("/favourite")
    public String viewFavouriteContacts(
        @RequestParam(value="page",defaultValue = "0")int page,
        @RequestParam(value = "size",defaultValue = "10")int size,
        @RequestParam(value ="sortBy",defaultValue="name")String sortBy,
        @RequestParam(value="direction",defaultValue = "asc")String direction
        ,Authentication authentication,Model model){


        //load all the user contacts
        //get all contacts by user id

        String username=Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(username);

        Page<Contact> pageContact=contactService.getByUserAndFavouriteTrue(user,page,size,sortBy,direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("searchForm", new SearchForm());


        return "user/favourite";
    }



    //Favourite Search API

    @RequestMapping("/searchFav")    
    public String favouritesearchHandler(
        @ModelAttribute SearchForm searchForm,
        @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value="page",defaultValue="0")int page,
        @RequestParam(value="sortBy",defaultValue = "name")String sortBy,
        @RequestParam(value="size",defaultValue ="asc")String direction,
        Model model,
        Authentication authentication
        
    ){

        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact=null;

        String field=searchForm.getField();
        String keyword=searchForm.getKeyword();

        if(field.equalsIgnoreCase("name")){

            pageContact=contactService.searchByNameAndFavourite(keyword,size,page,sortBy,direction,user);

        }else if(field.equalsIgnoreCase("email")){

            pageContact=contactService.searchByEmailAndFavourite(keyword,size,page,sortBy,direction,user);

        }else if(field.equalsIgnoreCase("phoneNumber")){

            pageContact=contactService.searchByPhoneNumberAndFavourite(keyword,size,page,sortBy,direction,user);

        }

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/searchFav";

    }





}
