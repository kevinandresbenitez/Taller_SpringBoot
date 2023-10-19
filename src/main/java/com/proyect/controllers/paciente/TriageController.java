package com.proyect.controllers.paciente;

import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.models.Triage;
import com.proyect.repositories.TriageRepository;
import com.proyect.services.MedicoService;
import com.proyect.services.PacienteService;
import com.proyect.services.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proyect.utils.TriageCalculador;
import com.proyect.utils.TriageObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/triages")
public class TriageController {
    @Autowired
    PacienteService pacienteService;
    @Autowired
    TriageService triageService;
    @Autowired
    MedicoService medicoService;
    
    @GetMapping("/")//pacientes a espera de ser triagiados
    public String listarTriages(Model model) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesNecesitanSerTriagados();
        model.addAttribute("pacientes",pacientes);
        return "pacientes/triages/index";
    }
    
    @GetMapping("/agregar/{id}") // Id del paciente a asignar triage
    public String mostrarFormulario(@PathVariable("id") Long id,Model model){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        model.addAttribute("paciente",paciente);
        return "pacientes/triages/crear";
    }
    
    @PostMapping("/agregar/{id}") // Procesamos el triage y lo guardamos
    public String calcularPuntuacionTotal(
            @PathVariable("id") Long id,
            @RequestParam("respiracion") int respiracion,
            @RequestParam("pulso") int pulso,
            @RequestParam("estadoMental") int estadoMental,
            @RequestParam("conciencia") int conciencia,
            @RequestParam("dolorPechoRespirar") int dolorPechoRespirar,
            @RequestParam("lesionesGraves") int lesionesGraves,
            @RequestParam("fiebre") int fiebre,
            @RequestParam("vomitos") int vomitos,
            @RequestParam("dolorAbdominal") int dolorAbdominal,
            @RequestParam("signosShock") int signosShock,
            @RequestParam("lesionesLeves") int lesionesLeves,
            @RequestParam("sangrado") int sangrado,
            Model model) {

        // Calculando triage
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        TriageCalculador triage = new TriageCalculador();
        triage.setRespiracion(respiracion);
        triage.setPulso(pulso);
        triage.setEstadoMental(estadoMental);
        triage.setConciencia(conciencia);
        triage.setDolorPechoRespirar(dolorPechoRespirar);
        triage.setLesionesGraves(lesionesGraves);
        triage.setFiebre(fiebre);
        triage.setVomitos(vomitos);
        triage.setDolorAbdominal(dolorAbdominal);
        triage.setSignosShock(signosShock);
        triage.setLesionesLeves(lesionesLeves);
        triage.setSangrado(sangrado);
        triage.setEdad(ChronoUnit.YEARS.between(paciente.getFechaNacimiento(),fechahoy));
        // Obteniendo punuacion y respectivo color, tiempo de espera ...
        TriageObject triageResultante =
                triage.segunPuntuacionObtenerTriageObject(triage.obtenerPuntuacion());
        
        // Guardamos un medico por defecto hasta implementar la session
        Optional<Medico> medico = medicoService.obtenerMedicoPorId(4L);
        
        
        // Creando el triage
        Triage triageAGuardar = new Triage();
        triageAGuardar.setColor(triageResultante.getColor());
        triageAGuardar.setPaciente(paciente);
        triageAGuardar.setFechaEvaluacion(fechahoy);
        triageAGuardar.setHoraEvaluacion(tiempohoy);
        triageAGuardar.setMedico(medico.get());
        triageService.guardarTriage(triageAGuardar);
        

        

        return "redirect:/triages/resultadotriage/"+id;
    }


    @GetMapping("/resultadotriage/{id}")//id del triage
    public String resultadoTriage(Model model,@PathVariable("id")Long id) {
        Triage triage = triageService.findTriageById(id);
        TriageCalculador obtenerDatosTriage = new TriageCalculador();
        TriageObject triageAMostrar = obtenerDatosTriage.segunColorObtenerTriageObject(triage.getColor());
        model.addAttribute("triage",triageAMostrar);
        return "pacientes/triages/resultadotriage";
    }

    @PostMapping("/cambiarcolor")
    public String cambiarColor(String nuevoColor, Model model) {
        /*
        if (niveles.containsKey(nuevoColor)) {

            this.colorNivel = nuevoColor;
            nivel = niveles.get(nuevoColor);
            tiempoEspera = tiemposDeEspera.get(nuevoColor);

            return "redirect:/cambio-color";
        } else {

            String mensaje = " El color no es válido.";
            model.addAttribute("mensaje", mensaje);
            return "pagina-de-error";
        }
        */
        return "resultado-triage";
    }

}
