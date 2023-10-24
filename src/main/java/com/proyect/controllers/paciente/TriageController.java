package com.proyect.controllers.paciente;

import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.models.Triage;
import com.proyect.models.TriageModificacion;
import com.proyect.repositories.TriageModificacionRepository;
import com.proyect.repositories.TriageRepository;
import com.proyect.services.MedicoService;
import com.proyect.services.PacienteService;
import com.proyect.services.TriageModificacionService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    TriageModificacionService triageModificacionService;
    
    @GetMapping("/")//pacientes a espera de ser triagiados
    public String listarTriages(Model model) {
        List<Paciente> pacientes = pacienteService.obtenerPacientesNecesitanSerTriagados();
        model.addAttribute("pacientes",pacientes);
        return "pacientes/triages/listaPacientes";
    }
    
    @GetMapping("/{id}")  // Id del paciente a mostrar sus triages
    public String listarTriagesPaciente(@PathVariable("id") Long id,Model model){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        if(paciente == null){
            return "redirect:/pacientes/";
        }
        
        model.addAttribute("triages",paciente.getTriages());
        return "pacientes/triages/index";
    }
    
    
    
    @GetMapping("/agregar/{id}") // Id del paciente a asignar triage
    public String mostrarFormulario(@PathVariable("id") Long id,Model model){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        
        if(paciente == null){
            return "redirect:/pacientes/";
        }
        
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
        
        if(paciente == null){
            return "redirect:/pacientes/";
        }
        
        
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
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size()-1);
        
        
        // Creando el triage
        Triage triageAGuardar = new Triage();
        triageAGuardar.setColor(triageResultante.getColor());
        triageAGuardar.setPaciente(paciente);
        triageAGuardar.setFechaEvaluacion(fechahoy);
        triageAGuardar.setHoraEvaluacion(tiempohoy);
        triageAGuardar.setMedico(medico);
        paciente.getTriages().add(triageAGuardar);
        triageService.guardarTriage(triageAGuardar);
        

        return "redirect:/triages/resultadotriage/"+id;
    }


    @GetMapping("/resultadotriage/{id}")//id del triage
    public String resultadoTriage(Model model,@PathVariable("id")Long id) {
        Triage triage = triageService.findTriageById(id);
        
        if(triage == null){
            return "redirect:/";
        }
        
        TriageCalculador obtenerDatosTriage = new TriageCalculador();
        TriageObject triageAMostrar = new TriageObject();
        triageAMostrar = obtenerDatosTriage.segunColorObtenerTriageObject(triage.getColor());
        model.addAttribute("triage",triageAMostrar);
        model.addAttribute("triage1",triage);
        return "pacientes/triages/resultadotriage";
    }
    @GetMapping("/cambiarcolor/{id}")//id del triage
    public String modificarTriage(Model model,@PathVariable("id")Long id) {
        Triage triage = triageService.findTriageById(id);
        
        if(triage == null){
            return "redirect:/";
        }
        
        model.addAttribute("triage",triage);
        return "pacientes/triages/modificacion";
    }

    @PostMapping("/cambiarcolor/{id}")
    public String cambiarColor(@PathVariable("id")Long id,
                               @RequestParam("nuevoColor") String nuevoColor,
                               @RequestParam("motivoDeCambio") String motivoDeCambio,
                               RedirectAttributes atributosMensaje) {

        Triage triage = triageService.findTriageById(id);
        if(triage == null){
            return "redirect:/";
        }
        
        TriageCalculador auxiliarTriageCalculador = new TriageCalculador();
        TriageObject compararNivelNuevo = auxiliarTriageCalculador.segunColorObtenerTriageObject(nuevoColor);
        TriageObject compararNivelViejo = auxiliarTriageCalculador.segunColorObtenerTriageObject(triage.getColor());
        if (compararNivelViejo.getNivel()+2<compararNivelNuevo.getNivel() || compararNivelViejo.getNivel()-2 > compararNivelNuevo.getNivel()) {
            atributosMensaje.addFlashAttribute("mensaje","¡No es posible cambiar mas de dos niveles!");
            return "redirect:/triages/cambiarcolor/"+id;
        } else {
            TriageModificacion triageModificacion = new TriageModificacion();
            triageModificacion.setTriage(triage);
            triageModificacion.setColorViejo(triage.getColor());
            triageModificacion.setMotivoDeCambio(motivoDeCambio);
            triage.setColor(compararNivelNuevo.getColor());
            triage.agregarModificacion(triageModificacion);
            triageService.guardarTriage(triage);
            triageModificacionService.guardarModificacion(triageModificacion);
            return "redirect:/triages/resultadotriage/"+id;
        }
    }

}
