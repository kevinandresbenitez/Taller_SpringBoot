/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud;
import com.proyect.models.Enfermero;
import com.proyect.models.Funcionario;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.services.AdministradorService;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
import com.proyect.services.ProfesionalSaludService;
import com.proyect.session.SessionUsuario;
import java.util.ArrayList;
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
@RequestMapping("/profesionalSalud")
public class ProfesionalSaludController {
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
    
    @GetMapping("/")
    public String list(Model modelo){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<ProfesionalSalud> profesionalesSalud = this.profesionalSaludService.listarProfesionalSalud();
        modelo.addAttribute("profesionalesSalud",profesionalesSalud);
        return "profesionalesSalud/index";
    }
    
    @GetMapping("/asignarFuncionario")
    public String selectFuncionary(Model model){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        model.addAttribute("funcionarios",funcionarios);
        return "profesionalesSalud/mostrarFuncionarios";
    }
    
    @GetMapping("/asignarFuncionario/{id}")
    public String showForm(@PathVariable("id") Long id, Model model,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como profesional de salud, ya que el funcionario no existe");
            return "redirect:/profesionalSalud/";
        }
        model.addAttribute("funcionario",funcionario.get());
        return "profesionalesSalud/asignarFuncionario";
    }
    
    @PostMapping("/asignarFuncionario/{id}")
    public String processAssing(@PathVariable("id") Long id,@RequestParam(name = "nroMatricula") Long nroMatricula,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        Boolean esAdministrador = this.administradorService.esFuncionarioAdministrador(id);
        Boolean esProfesionalSalud = this.profesionalSaludService.esFuncionarioProfesionalSalud(id);

        //Verificaciones del funcionario
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como profesional de salud, ya que el funcionario no existe");
            return "redirect:/profesionalSalud/";
        }
        if(esAdministrador){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como profesional de salud, ya que el funcionario es un administrador");
            return "redirect:/profesionalSalud/";
        }
        if(esProfesionalSalud){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como profesional de salud, ya que el funcionario ya fue asignado como profesional de la salud");
            return "redirect:/profesionalSalud/";
        }

        // Verificar si ya existe una matrícula igual
        Optional<ProfesionalSalud> profesionalSaludExistente = profesionalSaludService.findByNroMatricula(nroMatricula);
        if (profesionalSaludExistente.isPresent()) {
            atributosMensaje.addFlashAttribute("mensaje", "No se puede agregar como profesional de salud, ya que la matrícula " + nroMatricula + " ya está asignada.");
            return "redirect:/profesionalSalud/";
        }

        atributosMensaje.addFlashAttribute("mensaje","Se a asignado adecuadamente al funcionario como profesional de la salud");
        this.profesionalSaludService.asignarFuncionarioComoProfesionalSalud(funcionario.get(),nroMatricula);  
        return "redirect:/profesionalSalud/";
    }
        
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        this.profesionalSaludService.eliminarProfesionalSaludPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se revoco al funcionario como profesional de la salud");
        return "redirect:/profesionalSalud/";
    }
    
    
}
