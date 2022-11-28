package com.fullcycle.admin.catalogo.infraestructure;

import com.fullcycle.admin.catalogo.infraestructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class,args);
    }
}
