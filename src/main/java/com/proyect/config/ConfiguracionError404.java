package com.proyect.config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ConfiguracionError404 {


    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "redirect:/";
    }
}