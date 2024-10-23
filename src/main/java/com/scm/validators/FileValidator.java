package com.scm.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.scm.entities.Contact;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{


    // @Autowired
    // public Contact contact;


    public static final long MAX_FILE_SIZE=1024*104*2;  //2MB


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {


        //File blank check
        if(file==null || file.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("*File Cannot be empty").addConstraintViolation();
            return false;
        }

        //file size check
        // if(file.getSize()>MAX_FILE_SIZE){
        //     context.disableDefaultConstraintViolation();
        //     context.buildConstraintViolationWithTemplate("*File size exceeded > 2MB").addConstraintViolation();
        //     return false;
        // }

        //check for resolution using buffered image and ImageIO to check height and width

        return true;
    }

}
