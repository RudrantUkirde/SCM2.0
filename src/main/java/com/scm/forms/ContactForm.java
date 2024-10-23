package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "*Name is required")
    private String name;

    @Email(message = "*Invalid Email")
    @NotBlank(message = "*Email is required")
    private String email;

    @NotBlank(message = "*Phone Number Required")
    @Pattern(regexp = "^[0-9]{10}$",message="*Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "*Required")
    private String address;

    private String description;

    private boolean favourite;

    private String websiteLink;

    private String linkedInLink;

    //annotation to validate the image
    //size and resolution
    @ValidFile
    private MultipartFile contactImage;

}