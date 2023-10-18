package com.proyect.controllers.paciente;

import com.proyect.models.*;
import com.proyect.repositories.TriageRepository;
import com.proyect.services.ConsultaService;
import com.proyect.services.PacienteService;
import com.proyect.services.IngresoService;
import com.proyect.services.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.proyect.repositories.IngresoRepository;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;


    @GetMapping("/")
    public String list(Model model) {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/index";
    }


    @GetMapping("/crear")
    public String mostrarForm(@RequestParam("dni") Optional<Integer> dni,Model modelo) {
        
        if(dni.isPresent()){
            modelo.addAttribute("dni",dni.get());
        }
        
        return "/pacientes/crear";
    }



    @PostMapping("/crear")
    public String crearPaciente(@RequestParam("nombre") String nombre,
                                @RequestParam("dni") int dni,
                                @RequestParam("email") String email,
                                @RequestParam("domicilio") String domicilio,
                                @RequestParam("telefonoCelular") int telefonoCelular,
                                @RequestParam("telefonoFijo") int telefonoFijo,
                                @RequestParam("fechaNacimiento") LocalDate fechaNacimiento,
                                @RequestParam("estadoCivil") String estadoCivil) {
        Optional<Paciente> VerificarPaciente = pacienteService.findByDni(dni);
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setDni(dni);
        paciente.setTelefonoFijo(telefonoFijo);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setEmail(email);
        paciente.setTelefonoCelular(telefonoCelular);
        paciente.setDomicilio(domicilio);
        paciente.setEstadoCivil(estadoCivil);
        
                
        //Si esta creado ya, redirije a agregar ingreso
        if(VerificarPaciente.isPresent()){
            return "redirect:/pacientes/ingresos/agregar/"+VerificarPaciente.get().getId();
        }
        
        
        pacienteService.crearPaciente(paciente);
        return "redirect:/pacientes/ingresos/agregar/"+paciente.getId();
    }

    @GetMapping("/modificar/{id}")
    public String modificarPaciente(Model model, @PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (!paciente.isPresent()) {
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/modificar";
    }


}
