package com.proyect.controllers;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Order(Ordered.HIGHEST_PRECEDENCE)
@Controller
public class Error404Controller {

    /**
     * Funcion para redirijir accesos no validos
     *
     
     * @return Redireccion a /
     */
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "redirect:/";
    }
    
}