package com.authme.authme.config;

import com.authme.authme.data.service.CurrentUserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class ApplicationConfig {
    private final CurrentUserService currentUserService;

    public ApplicationConfig(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    public AuthMeMethodSecurityExpressionHandler expressionHandlerResolver(){
        return new AuthMeMethodSecurityExpressionHandler(currentUserService);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
