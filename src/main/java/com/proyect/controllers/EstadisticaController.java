package com.proyect.controllers;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.models.Triage;
import com.proyect.models.TriageModificacion;
import com.proyect.services.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@Controller
@Setter
@Getter
@RequestMapping("/estadisticas")
public class EstadisticaController {
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private TriageService triageService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private TriageModificacionService triageModificacionService;

    @GetMapping("/")
    public String mostrarAcciones(Model model){
        List<Medico> medicos = medicoService.listarMedicos();
        model.addAttribute("medicos",medicos);
        return "gestores/estadisticas";
    }

    @GetMapping("/cantidadpacientespormedico")
    public String cantidadPacientesAtendidosPorMedico(
            @RequestParam("idMedico") Long idMedico,
            @RequestParam("fechaInicio")LocalDate fechaInicio,
            @RequestParam("fechaFin")LocalDate fechaFin,Model model){
        Optional<Medico> medico = medicoService.obtenerMedicoPorId(idMedico);
        int pacientesPorMedico = consultaService.cantidadPacientesAtendidosPorMedico(medico.get(), fechaInicio, fechaFin);
        model.addAttribute("pacientesPorMedico",pacientesPorMedico);
        model.addAttribute("medico",medico.get());
        return "gestores/estadisticas";
    }

    @GetMapping("/cantidadpacientesporedadyfechaatencion")
    public String cantidadPacientesPorEdadYfechaAtencion(
            @RequestParam("edadMinima") int edadMinima,
            @RequestParam("edadMaxima") int edadMaxima,
            @RequestParam("fechaInicio")LocalDate fechaInicio,
            @RequestParam("fechaFin")LocalDate fechaFin, Model model) {
        int pacientesPorEdadYFecha = pacienteService.cantidadPacientesPorEdadYfechaAtencion(edadMinima, edadMaxima
                                                                                            , fechaInicio, fechaFin);
        model.addAttribute("pacientesPorEdadYFecha",pacientesPorEdadYFecha);
        return "gestores/estadisticas";
    }

    @GetMapping("/pacientemasconsultado")
    public String pacienteMasConsultaron(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin,Model model) {
        Paciente pacienteMasConsultado = pacienteService.pacienteMasConsultado(fechaInicio, fechaFin);
        model.addAttribute("pacienteMasConsultado",pacienteMasConsultado);
        return "gestores/estadisticas";
    }

    @GetMapping("/medicomasatendio")
    public String medicoMasAtendido(
            @RequestParam("fechaInicio")LocalDate fechaInicio,
            @RequestParam("fechaFin")LocalDate fechaFin,Model model) {
        Medico medicoMasAtendio = medicoService.medicoQueMasAtendio(fechaInicio, fechaFin);
        model.addAttribute("medicoMasAtendio",medicoMasAtendio);
        return "gestores/estadisticas";
    }
    @GetMapping("/triagesrangofechas")
    public String triagesRangoFechasYColorCantidad(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin")LocalDate fechaFin,Model model){

        List<Triage> triagesAUsar = triageService.findTriageEnRangoDeFechas(fechaInicio,fechaFin);
        int rojo = 0;
        int naranja = 0;
        int amarillo = 0;
        int verde = 0;
        int azul = 0;

        for (Triage triage : triagesAUsar) {
            if (Objects.equals(triage.getColor(), "Rojo")) {
                rojo++;
            } else if (Objects.equals(triage.getColor(), "Naranja")) {
                naranja++;
            } else if (Objects.equals(triage.getColor(), "Amarillo")) {
                amarillo++;
            } else if (Objects.equals(triage.getColor(), "Verde")) {
                verde++;
            } else {
                azul++;
            }
        }
        model.addAttribute("triages",triagesAUsar.size());
        model.addAttribute("rojo",rojo);
        model.addAttribute("naranja",naranja);
        model.addAttribute("amarillo",amarillo);
        model.addAttribute("verde",verde);
        model.addAttribute("azul",azul);

        return "gestores/estadisticas";
    }

    @GetMapping("/modificacionestriage")
    public String modificacionesTriage(Model model){
        List<TriageModificacion> modificaciones = triageModificacionService.listarModificacionesDeTriage();

        model.addAttribute("modificaciones",modificaciones);
        return "gestores/estadisticas";
    }
}