/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud;
import com.proyect.models.Funcionario;
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
 * Controlador para gestionar profesionales de la salud
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
    
    
   /**
 * Controlador para listar profesionales de la salud.
 *
 *
 * Se verifica si el usuario ha iniciado sesión.
 *
 * @param modelo  para agregar atributos a la vista.
 * @return Si el usuario ha iniciado sesión, muestra la vista que lista a los profesionales de la salud.
 *  Si no, redirige al usuario a la página de inicio.
 */
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
    
    
    /**
 * Controlador para seleccionar un funcionario para su asignación como profesional de la salud.
 *
 * Esta ruta permite a un administrador seleccionar un funcionario para asignarlo como profesional de la salud en el sistema.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador.
 *
 * @param model  para agregar atributos a la vista.
 * @return Si el usuario ha iniciado sesión, muestra la vista que lista a los funcionarios disponibles.
 *         Si no, redirige al usuario a la página de inicio.
 */
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
    
    
    /**
 * Controlador para mostrar el formulario de asignación de un funcionario como profesional de la salud.
 *
 *
 * @param id  Id del funcionario que se desea asignar como profesional de la salud.
 * @param model  para agregar atributos a la vista.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Si el usuario ha iniciado sesión, muestra el formulario para asignar un funcionario como profesional de la salud.
 *         Si el funcionario no existe, se agrega un mensaje de error y se redirige al usuario a la página de profesionales de la salud.
 *         Si el usuario no ha iniciado sesión, se redirige al usuario a la página de inicio.
 */
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
    
    /**
 * Controlador para  asignar de un funcionario como profesional de la salud.
 *
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador.
 *
 * @param id El ID del funcionario que se desea asignar como profesional de la salud.
 * @param nroMatricula El número de matrícula a asignar al funcionario como profesional de la salud.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Si el usuario ha iniciado sesión y es un administrador, procesa la asignación del funcionario como profesional de la salud.
 *         Realiza verificaciones para asegurarse de que el funcionario exista, no sea un administrador y no haya sido asignado como profesional de la salud previamente.
 *         También verifica si el número de matrícula ya existe.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige al usuario a la página de inicio.
 */
    
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
    
    
}
