package com.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
public class ProyectApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProyectApplication.class, args);

    }
    
    @GetMapping("/")
    public static ModelAndView home() {
        return new ModelAndView("index");
    }

}
