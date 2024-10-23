package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {

        //Generate the ID for user
        String id=UUID.randomUUID().toString();
        user.setUserId(id);

        //Password Encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));


        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {

        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user2=userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerifed(user.isEmailVerifed());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        User save=userRepo.save(user2);

        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String userId) {

        User user2=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {

        User user2=userRepo.findById(userId).orElse(null);

        return user2!=null?true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user=userRepo.findByEmail(email).orElse(null);

        return user!=null?true:false;

    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepo.findByEmail(email).orElse(null);
    }

}
