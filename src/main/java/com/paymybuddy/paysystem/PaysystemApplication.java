package com.paymybuddy.paysystem;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class PaysystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaysystemApplication.class, args);
    }

}
