package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    //save contact
    Contact save(Contact contact);

    //update
    Contact update(Contact contact);

    //all contacts
    List<Contact> getAll();

    //contact by id
    Contact getById(String id);

    //delete contact
    void delete(String id);

    //search by name
    Page<Contact> searchByName(String name,int size,int page,String sortBy,String direction,User user);

    //search by email
    Page<Contact> searchByEmail(String email,int size,int page,String sortBy,String direction,User user);

    //search by phone
    Page<Contact> searchByPhoneNumber(String phoneNumber,int size,int page,String sortBy,String direction,User user);


    //getContact by userid
    List<Contact> getByUserId(String userId);

    //get contact by User
    Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction);


    //Favourite Search

    //get all favourite contacts
    Page<Contact> getByUserAndFavouriteTrue(User user,int page,int size,String sortBy,String direction);


    //Get All favourite contacts with name
    Page<Contact> searchByNameAndFavourite(String name,int size,int page,String sortBy,String direction,User user);


    //Get All favourite contacts with email
    Page<Contact> searchByEmailAndFavourite(String email,int size,int page,String sortBy,String direction,User user);


    //Get All favourite contacts with phoneNumber
    Page<Contact> searchByPhoneNumberAndFavourite(String phoneNumber,int size,int page,String sortBy,String direction,User user);


}
