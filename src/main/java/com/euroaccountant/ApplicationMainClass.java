package com.euroaccountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ApplicationMainClass {

    public static void main(String[] args) {
        //Add this line to initialize bots context
        ApiContextInitializer.init();

        SpringApplication.run(ApplicationMainClass.class, args);
    }
}