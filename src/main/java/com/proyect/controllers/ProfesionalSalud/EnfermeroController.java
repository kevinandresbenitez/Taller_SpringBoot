/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud;
import com.proyect.models.Enfermero;
import com.proyect.models.ProfesionalSalud;
import com.proyect.services.AdministradorService;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
import com.proyect.services.ProfesionalSaludService;
import com.proyect.session.SessionUsuario;
import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * Coontrolador para gestionar Enfermeros
 */
@Controller
@Setter
@Getter
@RequestMapping("/profesionalSalud/enfermeros")
public class EnfermeroController {
    @Autowired
    private ProfesionalSaludService profesionalSaludService;
    @Autowired
    private EnfermeroService enfermeroService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    SessionUsuario sessionUser;
        
   /**
 * Controlador para listar enfermeros.
 *
 * Esta ruta permite a un administrador listar enfermeros en el sistema.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador.
 *
 * @param model  para agregar atributos a la vista.
 * @return Si el usuario ha iniciado sesión, muestra la vista de lista de enfermeros.
 *         Si no, redirige al usuario a la página de inicio.
 */
    @GetMapping("/")
    public String listEnfermereos(Model model){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Enfermero> enfermeros = this.enfermeroService.listarEnfermeros();
        model.addAttribute("enfermeros",enfermeros);
        return "profesionalesSalud/enfermeros/index";        
    }
    
    /**
 * Controlador para asignar enfermeros.
 *
 * Se verifica si el usuario ha iniciado sesión.
 *
 * @param model  para agregar atributos a la vista.
 * @return Si el usuario ha iniciado sesión, muestra la vista de asignación de enfermeros.
 *         Si no, redirige al usuario a la página de inicio.
 */
    @GetMapping("/asignar/")
    public String asignarEnfermero(Model model){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<ProfesionalSalud> profSalud = this.profesionalSaludService.listarProfesionalSalud();
        model.addAttribute("profesionalesSalud",profSalud);
        return "profesionalesSalud/enfermeros/asignar";        
    }
    
    /**
 * Controlador para procesar la asignación de un profesional de la salud como enfermero.
 *
 * Se verifica si el usuario ha iniciado sesión.
 *
 * @param id id del profesional de la salud que se desea asignar como enfermero.
 * @param atributosMensaje para agregar mensajes que se mostrarán después de la redirección.
 * @return Si el usuario ha iniciado sesión, procesa la asignación del profesional de la salud como enfermero y
 * redirige a la página de enfermeros.
 *  Si no, redirige al usuario a la página de inicio.
 */
    
    @GetMapping("/asignar/{id}")
    public String procesarAsignacionEnfermero(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Optional<ProfesionalSalud> profesionalSalud = this.profesionalSaludService.obtenerProfSaludPorId(id);
        Boolean esEnfermero = this.enfermeroService.esProfSaludEnfermero(id);
        Boolean esMedico = this.medicoService.esProfSaludMedico(id);
        
        if(profesionalSalud.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como enfermero, ya que el profesional de salud no existe");
            return "redirect:/profesionalSalud/enfermeros/";       
        }else if(esEnfermero){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como enfermero, ya que ya fue asignado como enfermero");
            return "redirect:/profesionalSalud/enfermeros/";                   
        }else if(esMedico){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como enfermero, ya que ya fue asignado como medico");
            return "redirect:/profesionalSalud/enfermeros/";       
        }
        
        atributosMensaje.addFlashAttribute("mensaje","El profesional de la salud fue asignado como enfermero correctamente");
        this.enfermeroService.asignarProfSaludComoEnfermero(profesionalSalud.get());
        return "redirect:/profesionalSalud/enfermeros/";          
    }
    

    
}
