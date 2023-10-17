package com.proyect.controllers;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.services.MedicoService;
import com.proyect.services.PacienteService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


@Controller
@Setter
@Getter
@RequestMapping("/estadisticas")
public class EstadisticaController {
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/cantidad-pacientes-por-medico")
    public int cantidadPacientesAtendidosPorMedico(
            @RequestParam("idMedico") int idMedico,
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        return medicoService.cantidadPacientesAtendidosPorMedico(idMedico, fechaInicio, fechaFin);
    }

    @GetMapping("/cantidad-pacientes-por-edad-y-fecha-atencion")
    public int cantidadPacientesPorEdadYfechaAtencion(
            @RequestParam("edadMinima") int edadMinima,
            @RequestParam("edadMaxima") int edadMaxima,
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        return pacienteService.cantidadPacientesPorEdadYfechaAtencion(edadMinima, edadMaxima, fechaInicio, fechaFin);
    }

    @GetMapping("/paciente-mas-consultado")
    public Paciente pacienteMasConsultado(
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        return pacienteService.pacienteMasConsultado(fechaInicio, fechaFin);
    }

    @GetMapping("/medico-mas-atendido")
    public Medico medicoMasAtendido(
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        return medicoService.medicoQueMasAtendio(fechaInicio, fechaFin);
    }
}