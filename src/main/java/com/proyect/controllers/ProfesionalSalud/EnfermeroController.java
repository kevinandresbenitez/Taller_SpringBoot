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
 * @author kevin
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
        
   
    @GetMapping("/")
    public String listEnfermereos(Model model){
        List<Enfermero> enfermeros = this.enfermeroService.listarEnfermeros();
        model.addAttribute("enfermeros",enfermeros);
        return "profesionalesSalud/enfermeros/index";        
    }
    
    @GetMapping("/asignar/")
    public String asignarEnfermero(Model model){
        List<ProfesionalSalud> profSalud = this.profesionalSaludService.listarProfesionalSalud();
        model.addAttribute("profesionalesSalud",profSalud);
        return "profesionalesSalud/enfermeros/asignar";        
    }
    
    @GetMapping("/asignar/{id}")
    public String procesarAsignacionEnfermero(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
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
    
    @GetMapping("/eliminar/{id}")
    public String eliminarEnfermero(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        this.enfermeroService.eliminarEnfermeroPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Eliminando enfermero adecuadamente");
        return "redirect:/profesionalSalud/enfermeros/";          
    }
    
}
