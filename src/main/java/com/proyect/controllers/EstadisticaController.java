package com.proyect.controllers;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.services.MedicoService;
import com.proyect.services.PacienteService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@Setter
@Getter
@RequestMapping("/estadisticas")
public class EstadisticaController {
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/")
    public String mostrarAcciones(Model model){
        List<Medico> medicos = medicoService.listarMedicos();
        model.addAttribute("medicos",medicos);
        return "gestores/estadisticas";
    }

    @GetMapping("/cantidadpacientespormedico")
    public String cantidadPacientesAtendidosPorMedico(
            @RequestParam("idMedico") Long idMedico,
            @RequestParam("fechaInicio")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,Model model) {
        int pacientesPorMedico = medicoService.cantidadPacientesAtendidosPorMedico(idMedico, fechaInicio, fechaFin);
        model.addAttribute("pacientesPorMedico",pacientesPorMedico);
        return "gestores/estadisticas";
    }

    @GetMapping("/cantidadpacientesporedadyfechaatencion")
    public String cantidadPacientesPorEdadYfechaAtencion(
            @RequestParam("edadMinima") int edadMinima,
            @RequestParam("edadMaxima") int edadMaxima,
            @RequestParam("fechaInicio")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin, Model model) {
        int pacientesPorEdadYFecha = pacienteService.cantidadPacientesPorEdadYfechaAtencion(edadMinima, edadMaxima, fechaInicio, fechaFin);
        model.addAttribute("pacientesPorEdadYFecha",pacientesPorEdadYFecha);
        return "gestores/estadisticas";
    }

    @GetMapping("/pacientemasconsultado")
    public String pacienteMasConsultaron(
            @RequestParam("fechaInicio")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,Model model) {
        Paciente pacienteMasConsultado = pacienteService.pacienteMasConsultado(fechaInicio, fechaFin);
        model.addAttribute("pacienteMasConsultado",pacienteMasConsultado);
        return "gestores/estadisticas";
    }

    @GetMapping("/medicomasatendio")
    public String medicoMasAtendido(
            @RequestParam("fechaInicio")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,Model model) {
        Medico medicoMasAtendio = medicoService.medicoQueMasAtendio(fechaInicio, fechaFin);
        model.addAttribute("medicoMasAtendio",medicoMasAtendio);
        return "gestores/estadisticas";
    }
}