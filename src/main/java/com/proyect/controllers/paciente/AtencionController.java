package com.proyect.controllers.paciente;

import com.proyect.models.*;
import com.proyect.repositories.TriageRepository;
import com.proyect.services.ConsultaService;
import com.proyect.services.PacienteService;
import com.proyect.services.IngresoService;
import com.proyect.services.TriageService;
import com.proyect.utils.TriageCalculador;
import com.proyect.utils.TriageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.proyect.services.BoxService;
import com.proyect.services.MedicoService;
import com.proyect.session.SessionUsuario;
import java.util.Objects;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * en esta clase se encuntran las operaciones correspondientes a todo el proceso de atencion
 * de un medico con respecto a los pacientes en emergencia
 */

@Controller
@RequestMapping("/pacientes/atenciones")
public class AtencionController {
    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    BoxService boxService;
    
    @Autowired
    MedicoService medicoService;

    @Autowired
    SessionUsuario sessionUser;

    /**
     *
     * @param model se utiliza para tener la posibilidad de mandar variables a la vista,
     *              en este caso pasamos un listado de boxes
     * @return vista del lugar donde estan siendo atendidos los pacientes
     */
    @GetMapping("/")
    public String list(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        List<Box> boxes = boxService.listarBoxes();
        
        model.addAttribute("boxes", boxes);
        return "pacientes/atenciones/index";
    }

    /**
     *
     * @param id id para ubicar un box en el sistema y verificar su estado
     * @param atributos nos permite enviar mensajes a la vista segun el estado de un box,
     *                  en este caso es para verificar si un box esta ocupado
     * @return vista de la seccion de atencion con los boxes y sus estados
     */
    @GetMapping("/trabajar/empezar/{id}")
    public String work(@PathVariable("id") Long id,RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si esta siendo ocupado por un medico
        if(box.isEmpty() || box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No existe el box o esta ocupado");
            return "redirect:/pacientes/atenciones/";
        }

        // Asignamos el medico al box
        box.get().setMedico(sessionUser.getMedic());
        boxService.guardarBox(box.get());

        atributos.addFlashAttribute("mensaje","Ahora estas trabajando en el box");
        return "redirect:/pacientes/atenciones/";
    }

    /**
     *
     * @param id id para ubicar un box en el sistema y verificar su estado
     * @param atributos nos permite enviar mensajes a la vista segun el estado de un box,
     *                  en este caso es para dejar libre un box
     * @return vista de la seccion de atencion con los boxes y sus estados
     */
    @GetMapping("/trabajar/terminar/{id}")
    public String workEnd(@PathVariable("id") Long id,RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si no hay nadie trabajando en el 
        if(box.isEmpty() || !box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No hay nadie atendiendo en el box");
            return "redirect:/pacientes/atenciones/";
        }

        if(Objects.equals(sessionUser.getMedic().getId(), box.get().getMedico().getId())){
            // Dejar vacio el box
            box.get().setMedico(null);
            boxService.guardarBox(box.get());
        }
        
        

        atributos.addFlashAttribute("mensaje","Dejaste de trabajar en el box");
        return "redirect:/pacientes/atenciones/";
    }


    /**
     *
     * @param id id para ubicar un box en el sistema y verificar su estado
     * @param atributos nos permite enviar mensajes a la vista segun el estado de un box,
     *                  en este caso es por si ocurren algunos inconvenientes al asignar o atender un paciente
     * @param model se utiliza para tener la posibilidad de mandar variables a la vista,
     *              en este caso pasamos un listado de pacientes y el id de un box
     * @return vista donde se encuentran pacientes triagiados esperando atencion medica
     */
    @GetMapping("/asignar/{id}")
    public String listaDePacientesAAsignar(@PathVariable("id") Long id,RedirectAttributes atributos,Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si no hay nadie trabajando en el 
        if(box.isEmpty() || !box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No hay nadie atendiendo en el box");
            return "redirect:/pacientes/atenciones/";
        }
        //Si en el box ya hay un paciente
        if(box.get().estaUnPacienteSiendoAtendido()){
            atributos.addFlashAttribute("mensaje","ya hay un paciente atendiendose aqui");
            return "redirect:/pacientes/atenciones/";
        }

        // Si el medico que va a asignar un paciente es el que esta trabajando alli
        if(!Objects.equals(sessionUser.getMedic().getId(), box.get().getMedico().getId())){
            atributos.addFlashAttribute("mensaje","No eres el medico que trabaja en el box este");
            return "redirect:/pacientes/atenciones/";
        }
        TriageObject triageObject = new TriageObject();
        List<Paciente> pacientes = pacienteService.obtenerPacientesNecesitanSerAtendidosEnBox();
        pacientes = pacientes.stream()
                .sorted(Comparator
                        .comparing((Paciente p)-> triageObject.ordenarColor(p.getTriages().get(p.getTriages().size()-1).getColor()))
                        .thenComparing((Paciente p)-> p.getTriages().get(p.getTriages().size()-1).getHoraEvaluacion()))
                .collect(Collectors.toList());//se ordenan los pacientes segun color, si resultan de igual color se ordena por
                                            //hora de evaluacion del triage
        model.addAttribute("pacientes",pacientes);
        model.addAttribute("id_box",id);
        return "/pacientes/atenciones/asignar";
    }

    /**
     *
     * @param id_box id de un box para ubicarlo en el sistema
     * @param id_paciente id de un paciente para ubicarlo en el sistema
     * @param atributos nos permite enviar mensajes a la vista segun el estado de un box,
     *                en este caso es por si ocurren algunos inconvenientes al asignar o
     *                  atender un paciente
     * @return vista de la seccion de atencion con los boxes y sus estados
     */
    @GetMapping("/asignar/{id}/{id_paciente}")
    public String procesarAsignacionDePaciente(@PathVariable("id") Long id_box,@PathVariable("id_paciente") Long id_paciente,RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        Optional<Box> box = boxService.obtenerBoxPorId(id_box);
        Optional<Paciente> paciente = pacienteService.obtenerPacienteById(id_paciente);
        
        
        //Si el box no existe o si no hay nadie trabajando en el 
        if(box.isEmpty() || !box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No hay nadie atendiendo en el box");
            return "redirect:/pacientes/atenciones/";
        }
        //Si en el box ya hay un paciente
        if(box.get().estaUnPacienteSiendoAtendido() || paciente == null){
            atributos.addFlashAttribute("mensaje","ya hay un paciente en este box o el paciente ingresado no existe");
            return "redirect:/pacientes/atenciones/";
        }
        
        // Si el medico que va a asignar un paciente es el que esta trabajando alli
        if(sessionUser.getMedic().getId() != box.get().getMedico().getId()){
            atributos.addFlashAttribute("mensaje","No eres el medico que trabaja en el box este");
            return "redirect:/pacientes/atenciones/";
        }
        
        // Asignamos el paciente al box
        box.get().setPaciente(paciente.get());
        boxService.guardarBox(box.get());
        atributos.addFlashAttribute("mensaje","Se agrego el paciente correctamente");
        return "redirect:/pacientes/atenciones/";
    }


    /**
     *
     * @param id_paciente id de un paciente para ubicarlo en el sistema
     * @param id id del box al cual le cambiaremos el estado
     * @param atributos manera de comunicarnos desde el controlador con la vista,
     *                  dependiendo el evento mandamos un mensaje
     * @return vista de la seccion de atencion con los boxes y sus estados
     */
    @GetMapping("/posponer/{id}/{id_paciente}")
    public String posponerConsulta(@PathVariable("id_paciente") Long id_paciente,@PathVariable("id") Long id,RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }
        
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si no hay nadie trabajando en el 
        if(box.isEmpty() || !box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No hay nadie atendiendo en el box");
            return "redirect:/pacientes/atenciones/";
        }
        //Si en el box no hay un paciente
        if(!box.get().estaUnPacienteSiendoAtendido()){
            atributos.addFlashAttribute("mensaje","no hay un paciente siendo atendido en este box");
            return "redirect:/pacientes/atenciones/";
        }

        
        // Si el medico que va a posponer la consulta de un paciente es el que esta trabajando alli
        if(!Objects.equals(sessionUser.getMedic().getId(), box.get().getMedico().getId())){
            atributos.addFlashAttribute("mensaje","No eres el medico que trabaja en el box este");
            return "redirect:/pacientes/atenciones/";
        }
        
        //Pospongo la atencion del paciente
        box.get().setPaciente(null);
        boxService.guardarBox(box.get());
        
        atributos.addFlashAttribute("mensaje","Se pospuso la atencion del paciente");
        return "redirect:/pacientes/atenciones/";
    }
    
}
