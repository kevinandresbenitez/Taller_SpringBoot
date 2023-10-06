package com.proyect.controllers;

import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/")
    public String list(Model model){
        List<Paciente> pacientes =pacienteService.listarPacientes();
        model.addAttribute("pacientes",pacientes);
        return"pacientes/index";
    }
    @GetMapping("/crear")
    public String mostrarForm(){
        return "/pacientes/crear";
    }
    @PostMapping("/crear")
    public String crearPaciente(@RequestParam("nombre") String nombre,@RequestParam("dni") int dni,@RequestParam("email")String email,@RequestParam("domicilio")String domicilio,@RequestParam("telefonoCelular")int telefonoCelular,@RequestParam("telefonoFijo")int telefonoFijo,@RequestParam("fechaNacimiento")String fechaNacimiento,@RequestParam("estadoCivil")String estadoCivil){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setDni(dni);
        paciente.setTelefonoFijo(telefonoFijo);
        paciente.setEmail(email);
        paciente.setTelefonoCelular(telefonoCelular);
        paciente.setDomicilio(domicilio);
        paciente.setEstadoCivil(estadoCivil);
        pacienteService.crearPaciente(paciente);
        return "redirect:/pacientes/";
    }

    @GetMapping("/modificar/{id}")
    public String modificarPaciente(Model model, @PathVariable("id")Long id){
        Optional<Paciente> paciente = pacienteService.findById(id);
        if(!paciente.isPresent()){
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente",paciente.get());
        return"pacientes/modificar";
    }
    /*
    @GetMapping("/resultadosestudios/index/{id}")
    public String listaResultadoEstudios(Model model,@PathVariable("id")Long id){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        if(paciente.getId()==null){
            return "redirect:/pacientes/";
        }
        model.addAttribute("resultadoEstudios",paciente.getResultadoEstudios());
        model.addAttribute("paciente",paciente);
        return "/pacientes/resultadoestudios/index";
    }
    @GetMapping("/resultadoestudios/crear/{id}")
    public String crearResultadoEstudios(){
        return "/pacientes/resultadoestudios/index";
    }

    @PostMapping("/resultadoestudios/crear/{id}")
    public String crearResultadoEstudios(@RequestParam("fecha") Date fecha,@RequestParam("hora")String hora,@RequestParam("tipoInforme") String tipoInforme,@RequestParam("informeEstudio")String informeEstudio, @PathVariable("id")Long id){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        ResultadoEstudio resultadoEstudiosNuevo = new ResultadoEstudio();
        resultadoEstudiosNuevo.setInformeEstudio(informeEstudio);
        resultadoEstudiosNuevo.setHora(hora);
        resultadoEstudiosNuevo.setFecha(fecha);
        resultadoEstudiosNuevo.setTipoInforme(tipoInforme);
        paciente.agregarResultadoEstudios(resultadoEstudiosNuevo);
        return"/pacientes/resultadoestudios/"+paciente.getId();
    }
    */
}
