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
import com.proyect.session.SessionUsuario;
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
/** 
 * Controlador para gestinar Triages
 
 */
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
    @Autowired
    SessionUsuario sessionUser;
    
    /**
 * Controlador para mostrar la lista de pacientes que están a la espera de ser triagiados.
 *
 * @param model  para agregar datos que se mostrarán en la vista.
 * @return La vista que muestra la lista de pacientes a la espera de ser triagiados o redirige a la página de inicio,
 * si el usuario no tiene acceso.
 */
    @GetMapping("/")//pacientes a espera de ser triagiados
    public String listarTriages(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
        List<Paciente> pacientes = pacienteService.obtenerPacientesNecesitanSerTriagados();
        model.addAttribute("pacientes",pacientes);
        return "pacientes/triages/listaPacientes";
    }
    
    
    /**
 * MControlador  para mostrar los triages de un paciente específico por su ID.
 *
 * @param id  Id del paciente cuyos triages se deben mostrar.
 * @param model  para agregar datos que se mostrarán en la vista.
 * @return La vista que muestra los triages del paciente o redirige a la página de inicio si el paciente no se encuentra,
 * o si el usuario no tiene acceso.
 */
    @GetMapping("/{id}")  // Id del paciente a mostrar sus triages
    public String listarTriagesPaciente(@PathVariable("id") Long id,Model model){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        if(paciente == null){
            return "redirect:/pacientes/";
        }
        
        model.addAttribute("triages",paciente.getTriages());
        return "pacientes/triages/index";
    }
    
    
    /**
 * Controlador para mostrar el formulario de asignación de triage a un paciente específico por su Id.
 *
 * @param id  Id del paciente al que se asignará un triage.
 * @param model  para agregar datos que se mostrarán en la vista.
 * @return La vista que muestra el formulario de asignación de triage o redirige a la página de inicio si el paciente no se encuentra
 * o si el usuario no tiene acceso.
 */
    @GetMapping("/agregar/{id}") // Id del paciente a asignar triage
    public String mostrarFormulario(@PathVariable("id") Long id,Model model){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        
        if(paciente == null){
            return "redirect:/pacientes/";
        }
        
        model.addAttribute("paciente",paciente);
        return "pacientes/triages/crear";
    }
    
    
    /**
 * Metodo para procesar la evaluación de triage y guardarla en la base de datos.
 *
 * @param id El Id del paciente al que se le asignará un triage.
 * @param respiracion Puntuación de evaluación de la respiración.
 * @param pulso Puntuación de evaluación del pulso.
 * @param estadoMental Puntuación de evaluación del estado mental.
 * @param conciencia Puntuación de evaluación de la conciencia.
 * @param dolorPechoRespirar Puntuación de evaluación del dolor en el pecho al respirar.
 * @param lesionesGraves Puntuación de evaluación de lesiones graves.
 * @param fiebre Puntuación de evaluación de fiebre.
 * @param vomitos Puntuación de evaluación de vómitos.
 * @param dolorAbdominal Puntuación de evaluación de dolor abdominal.
 * @param signosShock Puntuación de evaluación de signos de shock.
 * @param lesionesLeves Puntuación de evaluación de lesiones leves.
 * @param sangrado Puntuación de evaluación de sangrado.
 * @param model  para agregar datos que se mostrarán en la vista.
 * @return La redirección a la vista que muestra el resultado de la evaluación de triage o redirige a la página de inicio
 * si el paciente no se encuentra o si el usuario no tiene acceso.
 */
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
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
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
        TriageObject triageResultante = triage.segunPuntuacionObtenerTriageObject(triage.obtenerPuntuacion());
        
        
        // Creando el triage
        Triage triageAGuardar = new Triage();
        triageAGuardar.setColor(triageResultante.getColor());
        triageAGuardar.setPaciente(paciente);
        triageAGuardar.setFechaEvaluacion(fechahoy);
        triageAGuardar.setHoraEvaluacion(tiempohoy);
        triageAGuardar.setProfesionalSalud(sessionUser.getProfesionalSalud());
        paciente.getTriages().add(triageAGuardar);
        triageService.guardarTriage(triageAGuardar);
        

        return "redirect:/triages/resultadotriage/"+triageAGuardar.getId();
    }

    /**
 * Metodo para mostrar el resultado de la evaluación de triage según su Id.
 *
 * @param model  para agregar datos que se mostrarán en la vista.
 * @param id El Id del triage que se mostrará.
 * @return La vista que muestra el resultado de la evaluación de triage o redirige a la página de inicio si el triage no se encuentra
 * o si el usuario no tiene acceso.
 */
    @GetMapping("/resultadotriage/{id}")//id del triage
    public String resultadoTriage(Model model,@PathVariable("id")Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
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
    
    /**
 * Metodo para mostrar el formulario de modificación de color de triage.
 *
 * @param model  para agregar datos que se mostrarán en la vista.
 * @param id Id del triage que se modificará.
 * @return La vista del formulario de modificación de color de triage o redirige a la página de inicio si el triage no se encuentra
 * o si el usuario no tiene acceso.
 */
    @GetMapping("/cambiarcolor/{id}")//id del triage
    public String modificarTriage(Model model,@PathVariable("id")Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
        Triage triage = triageService.findTriageById(id);
        
        if(triage == null){
            return "redirect:/";
        }
        
        model.addAttribute("triage",triage);
        return "pacientes/triages/modificacion";
    }
    
    
    /**
 * Metodo para cambiar el color de triage de un paciente.
 *
 * @param id Id del triage que se modificará.
 * @param nuevoColor  nuevo color de triage seleccionado.
 * @param motivoDeCambio  motivo o justificación para el cambio de color.
 * @param atributosMensaje  para agregar mensajes flash que se mostrarán después de redirigir.
 * @return Redirige al resultado del triage modificado o al formulario de modificación en caso de error.
 */
    @PostMapping("/cambiarcolor/{id}")
    public String cambiarColor(@PathVariable("id")Long id,
                               @RequestParam("nuevoColor") String nuevoColor,
                               @RequestParam("motivoDeCambio") String motivoDeCambio,
                               RedirectAttributes atributosMensaje) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isProfSalud()){
            return "redirect:/";
        }
        
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
