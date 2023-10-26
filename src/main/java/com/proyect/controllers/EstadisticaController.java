package com.proyect.controllers;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.models.Triage;
import com.proyect.models.TriageModificacion;
import com.proyect.services.*;
import com.proyect.session.SessionUsuario;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


/**
 * Controlador para la gestion de estadísticas en la aplicacion
 */
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
    @Autowired
    SessionUsuario sessionUser;
    
    /**
     * Muestra la pagina de estadísticas con la lista de medicos
     * en el primer formulario.
     *
     * @param model el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/")
    public String mostrarAcciones(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        List<Medico> medicos = medicoService.listarMedicos();
        model.addAttribute("medicos", medicos);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene la cantidad de pacientes atendidos por un medico en un rango de fechas
     *
     * @param idMedico     id del medico para obtener un objeto medico
     * @param fechaInicio  fecha desde donde se quiere buscar
     * @param fechaFin     fecha hasta donde se quiere buscar
     * @param model        el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/cantidadpacientespormedico")
    public String cantidadPacientesAtendidosPorMedico(
            @RequestParam("idMedico") Long idMedico,
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin, Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        Optional<Medico> medico = medicoService.obtenerMedicoPorId(idMedico);
        int pacientesPorMedico = consultaService.cantidadPacientesAtendidosPorMedico(medico.get(), fechaInicio, fechaFin);
        model.addAttribute("pacientesPorMedico", pacientesPorMedico);
        model.addAttribute("medico", medico.get());
        cargarDatosMedicos(model);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene la cantidad de pacientes por edad y fecha de atencion
     *
     * @param edadMinima   edad minima para filtrar pacientes
     * @param edadMaxima   edad maxima para filtrar pacientes
     * @param fechaInicio  fecha desde donde se quiere buscar
     * @param fechaFin     fecha hasta donde se quiere buscar
     * @param model        el modelo utilizado para pasar variables a la vista
     * @return la vista de estadisticas
     */
    @GetMapping("/cantidadpacientesporedadyfechaatencion")
    public String cantidadPacientesPorEdadYfechaAtencion(
            @RequestParam("edadMinima") int edadMinima,
            @RequestParam("edadMaxima") int edadMaxima,
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin, Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        int pacientesPorEdadYFecha = pacienteService.cantidadPacientesPorEdadYfechaAtencion(edadMinima, edadMaxima
                , fechaInicio, fechaFin);
        model.addAttribute("pacientesPorEdadYFecha", pacientesPorEdadYFecha);
        cargarDatosMedicos(model);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene el paciente mas consultado en un rango de fechas
     *
     * @param fechaInicio  fecha desde donde se quiere buscar
     * @param fechaFin     fecha hasta donde se quiere buscar
     * @param model        el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/pacientemasconsultado")
    public String pacienteMasConsultaron(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin, Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        Paciente pacienteMasConsultado = pacienteService.pacienteMasConsultado(fechaInicio, fechaFin);
        model.addAttribute("pacienteMasConsultado", pacienteMasConsultado);
        cargarDatosMedicos(model);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene el medico que mas atendio en un rango de fechas
     *
     * @param fechaInicio  fecha desde donde se quiere buscar
     * @param fechaFin     fecha hasta donde se quiere buscar
     * @param model        el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/medicomasatendio")
    public String medicoMasAtendido(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin, Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        Medico medicoMasAtendio = medicoService.medicoQueMasAtendio(fechaInicio, fechaFin);
        model.addAttribute("medicoMasAtendio", medicoMasAtendio);
        cargarDatosMedicos(model);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene la cantidad de triages por color en un rango de fechas
     *
     * @param fechaInicio  fecha desde donde se quiere buscar
     * @param fechaFin     fecha hasta donde se quiere buscar
     * @param model        el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/triagesrangofechas")
    public String triagesRangoFechasYColorCantidad(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin, Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        

        List<Triage> triagesAUsar = triageService.findTriageEnRangoDeFechas(fechaInicio, fechaFin);
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
        model.addAttribute("triages", triagesAUsar.size());
        model.addAttribute("rojo", rojo);
        model.addAttribute("naranja", naranja);
        model.addAttribute("amarillo", amarillo);
        model.addAttribute("verde", verde);
        model.addAttribute("azul", azul);
        cargarDatosMedicos(model);
        return "gestores/estadisticas";
    }

    /**
     * Obtiene las modificaciones de triage
     *
     * @param model el modelo utilizado para pasar variables a la vista
     * @return la vista de estadísticas
     */
    @GetMapping("/modificacionestriage")
    public String modificacionesTriage(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Gestor")){
            return "redirect:/";
        }
        
        List<TriageModificacion> modificaciones = triageModificacionService.listarModificacionesDeTriage();
        cargarDatosMedicos(model);
        model.addAttribute("modificaciones", modificaciones);
        return "gestores/estadisticas";
    }

    /**
     * Esta funcion carga de nuevo los medicos del primer formulario
     *
     * @param model el modelo utilizado para pasar variables a la vista.
     */
    private void cargarDatosMedicos(Model model) {
        List<Medico> medicos = medicoService.listarMedicos();
        model.addAttribute("medicos", medicos);
    }
}
