package com.paymybuddy.paysystem.config;

import com.paymybuddy.paysystem.web.controller.PersonController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ResourceBundle;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        logger.info("security start");
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String myUser = bundle.getString("application.security.user.name");
        String myPwd = bundle.getString("application.security.user.password");
        myPwd = "{noop}" +myPwd;
        logger.info( myUser + "  -  " +myPwd);

     auth.inMemoryAuthentication()
                .withUser(myUser)
                .password(myPwd)
                .roles("USER");

  /*      auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER");*/
    }
}
