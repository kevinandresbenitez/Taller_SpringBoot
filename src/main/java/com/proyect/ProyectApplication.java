package com.proyect;

import com.proyect.session.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class ProyectApplication {

    @Autowired
    SessionUsuario sessionUser;
    
    public static void main(String[] args) {
        SpringApplication.run(ProyectApplication.class, args);
    }
    
    @GetMapping("/")
    public String home() {
        // Verificacion de session
        if(!sessionUser.existSession()){
            return "redirect:/login/";
        }
        return "index";
    }

}
