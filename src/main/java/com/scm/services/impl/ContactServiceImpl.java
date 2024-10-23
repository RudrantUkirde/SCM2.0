package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;


@Service
public class ContactServiceImpl implements ContactService{


    @Autowired
    private ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {

        String contactId=UUID.randomUUID().toString();

        contact.setId(contactId);

        return contactRepo.save(contact);

    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Contact> getAll() {

        return contactRepo.findAll();

    }

    @Override
    public Contact getById(String id) {

        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact with given id not found"));
    }

    @Override
    public void delete(String id) {

        var contact=contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact with given id not found"));

        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {

       return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        

        var pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String direction,User user) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndNameContaining(user,name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String direction,User user) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String direction,User user) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumber,pageable);
    }

    @Override
    public Page<Contact> getByUserAndFavouriteTrue(User user, int page, int size, String sortBy, String direction) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndFavouriteTrue(user,pageable);

    }

    @Override
    public Page<Contact> searchByNameAndFavourite(String name, int size, int page, String sortBy, String direction,
            User user) {

            Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
            var pageable=PageRequest.of(page, size,sort);
            return contactRepo.findByUserAndNameAndFavouriteTrue(user, name, pageable);

    }

    @Override
    public Page<Contact> searchByEmailAndFavourite(String email, int size, int page, String sortBy, String direction,
            User user) {

            Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
            var pageable=PageRequest.of(page, size,sort);
            return contactRepo.findByUserAndEmailAndFavouriteTrue(user, email, pageable);

    }

    @Override
    public Page<Contact> searchByPhoneNumberAndFavourite(String phoneNumber, int size, int page, String sortBy,
            String direction, User user) {

            Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
            var pageable=PageRequest.of(page, size,sort);
            return contactRepo.findByUserAndPhoneNumberAndFavouriteTrue(user, phoneNumber, pageable);

    }


}