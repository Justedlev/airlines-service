package com.justedlev.service.airlines.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

   @Bean
   PasswordEncoder getPasswordEncoder() {
       return new BCryptPasswordEncoder();
   }

}
