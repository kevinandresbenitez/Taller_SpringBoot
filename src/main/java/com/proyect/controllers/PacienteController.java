package com.proyect.controllers;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.ConsultaService;
import com.proyect.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;
    ConsultaService consultaService;
    @GetMapping("/")
    public String list(Model model) {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/index";
    }

    @GetMapping("/crear")
    public String mostrarForm() {
        return "/pacientes/crear";
    }

    @PostMapping("/crear")
    public String crearPaciente(@RequestParam("nombre") String nombre,
                                @RequestParam("dni") int dni,
                                @RequestParam("email") String email,
                                @RequestParam("domicilio") String domicilio,
                                @RequestParam("telefonoCelular") int telefonoCelular,
                                @RequestParam("telefonoFijo") int telefonoFijo,
                                @RequestParam("fechaNacimiento") String fechaNacimiento,
                                @RequestParam("estadoCivil") String estadoCivil) {
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
    public String modificarPaciente(Model model, @PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (!paciente.isPresent()) {
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/modificar";
    }

    @GetMapping("/consultas/{id}")
    public String listaResultadoEstudios(Model model, @PathVariable("id") Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        if (paciente.getId() == null) {
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente", paciente);
        return "/pacientes/consultas/index";
    }


    @PostMapping("/consultas/agregar/{id}")
    public String crearConsultas(@RequestParam("resultadoestudios") List<ResultadoEstudio> resultadoEstudios,
                                 @RequestParam("fecha") LocalDate fecha,
                                 @RequestParam("hora") LocalTime hora,
                                 @RequestParam("diagnostico") String diagnostico,
                                 @RequestParam("tipoAtencion") String tipoAtencion,
                                 @PathVariable("diagnosticosClinicos") String diagnosticosClinicos,
                                 @RequestParam("id") Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Consulta consulta = new Consulta();
        consulta.setHoraAtencion(hora);
        consulta.setFechaAtencion(fecha);
        consulta.setTipoAtencion(tipoAtencion);
        consulta.setPaciente(paciente);
        consulta.setDiagnostico(diagnostico);
        consulta.setResultadosEstudios(resultadoEstudios);
        consultaService.guardarConsulta(consulta);
        paciente.agregarConsultas(consulta);
        return "/pacientes/resultadoestudios/" + paciente.getId();
    }

}
