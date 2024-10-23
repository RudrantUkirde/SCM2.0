package com.scm.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    //Configuration of authentication provider spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        
        DaoAuthenticationProvider  daoAuthenticationProvider=new DaoAuthenticationProvider();

        //User detail service Object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        //password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //configuration
        httpSecurity.authorizeHttpRequests(authorize -> 
                {
                    authorize.requestMatchers("/user/**").authenticated();
                    authorize.anyRequest().permitAll();
                });

        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .successForwardUrl("/user/profile")
            .failureForwardUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password");
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(formLogout ->{
            formLogout.logoutUrl("/logout");
            formLogout.logoutSuccessUrl("/login?logout=true");
        });

        //Oauth 2.0 configurations
        httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
