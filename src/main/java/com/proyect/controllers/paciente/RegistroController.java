package com.proyect.controllers.paciente;


import com.proyect.services.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/pacientes/registros")
public class RegistroController {
    @Autowired
    RegistroService registroService;

    @GetMapping("/")
    public String list(Model model) {

        return "pacientes/index";
    }



}
