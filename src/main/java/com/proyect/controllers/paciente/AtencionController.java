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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes/atenciones")
public class AtencionController {
    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    BoxService boxService;
    
    @Autowired
    MedicoService medicoService;



    @GetMapping("/")
    public String list(Model model) {
        List<Box> boxes = boxService.listarBoxes();
        
        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );

        model.addAttribute("boxes", boxes);
        model.addAttribute("medicoSession", medico);
        return "pacientes/atenciones/index";
    }
    
    @GetMapping("/trabajar/empezar/{id}")
    public String work(@PathVariable("id") Long id,RedirectAttributes atributos) {
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si esta siendo ocupado por un medico
        if(box.isEmpty() || box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No existe el box o esta ocupado");
            return "redirect:/pacientes/atenciones/";
        }

        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        // Asignamos el medico al box
        box.get().setMedico(medico);
        boxService.guardarBox(box.get());

        atributos.addFlashAttribute("mensaje","Ahora estas trabajando en el box");
        return "redirect:/pacientes/atenciones/";
    }
    
    @GetMapping("/trabajar/terminar/{id}")
    public String workEnd(@PathVariable("id") Long id,RedirectAttributes atributos) {
        Optional<Box> box = boxService.obtenerBoxPorId(id);
        
        //Si el box no existe o si no hay nadie trabajando en el 
        if(box.isEmpty() || !box.get().estaUnMedicoAtendiendo()){
            atributos.addFlashAttribute("mensaje","No hay nadie atendiendo en el box");
            return "redirect:/pacientes/atenciones/";
        }

        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        
        if(medico.getId() == box.get().getMedico().getId()){
            // Dejar vacio el box
            box.get().setMedico(null);
            boxService.guardarBox(box.get());
        }
        
        

        atributos.addFlashAttribute("mensaje","Dejaste de trabajar en el box");
        return "redirect:/pacientes/atenciones/";
    }


    
    @GetMapping("/asignar/{id}")
    public String listaDePacientesAAsignar(@PathVariable("id") Long id,RedirectAttributes atributos,Model model) {
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

        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        
        // Si el medico que va a asignar un paciente es el que esta trabajando alli
        if(medico.getId() != box.get().getMedico().getId()){
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
    
    @GetMapping("/asignar/{id}/{id_paciente}")
    public String procesarAsignacionDePaciente(@PathVariable("id") Long id_box,@PathVariable("id_paciente") Long id_paciente,RedirectAttributes atributos,Model model) {
        Optional<Box> box = boxService.obtenerBoxPorId(id_box);
        Paciente paciente = pacienteService.obtenerPacienteById(id_paciente);
        
        
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

        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        
        // Si el medico que va a asignar un paciente es el que esta trabajando alli
        if(medico.getId() != box.get().getMedico().getId()){
            atributos.addFlashAttribute("mensaje","No eres el medico que trabaja en el box este");
            return "redirect:/pacientes/atenciones/";
        }
        
        // Asignamos el paciente al box
        box.get().setPaciente(paciente);
        boxService.guardarBox(box.get());
        atributos.addFlashAttribute("mensaje","Se agrego el paciente correctamente");
        return "redirect:/pacientes/atenciones/";
    }
    
    @GetMapping("/posponer/{id}/{id_paciente}")
    public String posponerConsulta(@PathVariable("id_paciente") Long id_paciente,@PathVariable("id") Long id,RedirectAttributes atributos,Model model) {
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

        //Hasta que se implemente la session un medico cualquiera es el que esta atendiendo
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        
        // Si el medico que va a posponer la consulta de un paciente es el que esta trabajando alli
        if(medico.getId() != box.get().getMedico().getId()){
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
