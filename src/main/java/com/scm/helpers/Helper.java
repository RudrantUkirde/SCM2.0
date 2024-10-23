package com.scm.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.var;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken){

            var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;

            var clientId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User=(OAuth2User)authentication.getPrincipal();

            String userName="";

            if(clientId.equalsIgnoreCase("google")){

                //signed in with Google
                System.out.println("Getting Email from google");
                userName=oauth2User.getAttribute("email").toString();
            }

            return userName; 
        }

        System.out.println("SELF Login Email");
        return authentication.getName();

    }

}
