package com.proyect.controllers;

import com.proyect.models.Paciente;
import com.proyect.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/")
    public String listaPaciente(Model model){
        List<Paciente> pacientes =pacienteService.listarPacientes();
        model.addAttribute("funcionarios",pacientes);
        return"pacientes/listarpacientes";
    }
    @PostMapping("/agregarpacientes")
    public String crearPaciente(@RequestParam("nombre") String nombre,@RequestParam("dni") int dni){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setDni(dni);
        pacienteService.crearPaciente(paciente);
        return "redirect:/pacientes/";
    }

}
