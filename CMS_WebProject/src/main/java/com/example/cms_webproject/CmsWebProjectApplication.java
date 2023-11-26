package com.example.cms_webproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CmsWebProjectApplication {

    @Bean
    Hibernate5JakartaModule hibernate5Module() {
        return new Hibernate5JakartaModule();
    }

    public static void main(String[] args) {
        SpringApplication.run(CmsWebProjectApplication.class, args);
        //SpringApplication app = new SpringApplication(CmsWebProjectApplication.class);
        //app.run(args);
    }

}
