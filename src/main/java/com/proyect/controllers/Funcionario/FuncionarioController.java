/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.Funcionario;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.services.FuncionarioService;
import com.proyect.services.RolService;
import com.proyect.services.SectorService;
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
 * Controlador para gestionar funcionarios
 */
@Controller
@Setter
@Getter
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private RolService rolServices;
    @Autowired
    private SectorService sectorService;
    @Autowired
    SessionUsuario sessionUser;
    
    
    /**
 * Controlador para listar los funcionarios.
 *
 * Esta ruta permite a un administrador listar todos los funcionarios registrados en la aplicación.
 * Se verifica si el usuario ha iniciado sesión  antes de mostrar la lista de funcionarios.
 *
 * @param modelo Objeto Model para agregar datos que se mostrarán en la vista.
 * @return Muestra la lista de funcionarios en la vista "funcionarios/index" si el usuario ha iniciado sesión.
 *         Si el usuario no ha iniciado sesión, se redirige a la página de inicio.
 */
    @GetMapping("/")
    public String list(Model modelo){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        modelo.addAttribute("funcionarios",funcionarios);
        return "funcionarios/index";
    }
    
    /**
 * Controlador para mostrar el formulario de creación de funcionarios.
 *
 * Esta ruta permite a un administrador acceder al formulario para crear un nuevo funcionario.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador antes de mostrar el formulario.
 *
 * @return Muestra el formulario de creación de funcionarios en la vista "funcionarios/crear" si el usuario ha iniciado sesión y es un administrador.
 *  Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 */
    @GetMapping("/crear")
    public String createForm(){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }        
        return "funcionarios/crear";
    }
    
    /**
 * Controlador para procesar el formulario de creación de funcionarios.
 *
 * Esta ruta permite a un administrador procesar los datos enviados desde el formulario para crear un nuevo funcionario.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador.
 *
 * @param funcionario  Objeto Funcionario que contiene los datos del nuevo funcionario a crear.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Redirige a la página de roles de funcionarios con un mensaje de creación exitosa si se crea el funcionario correctamente.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 *         Si ya existe un funcionario con el mismo DNI, se redirige al formulario de creación con un mensaje de error.
 */
    @PostMapping("/crear")
    public String processFormCreation(@ModelAttribute Funcionario funcionario,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        Optional<Funcionario> funcionarioPrevio = this.funcionarioServices.obtenerFuncionarioPorDni(funcionario.getDni());
        if(funcionarioPrevio.isPresent()){
            atributosMensaje.addFlashAttribute("mensaje","No se puede crear el funcionario por que ya existe");
            return "redirect:/funcionarios/crear";
        }
        
        
        funcionario.setContraseña(Integer.toString(funcionario.getDni()));
        this.funcionarioServices.crearFuncionario(funcionario);
        
        //Una vez creado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Se creo un funcionario Adecuadamente");
        return "redirect:/funcionarios/";
    }    
        
   /**
 * Controlador para mostrar el formulario de modificación de contraseña.
 *
 * Esta ruta permite a un usuario acceder al formulario para cambiar su contraseña.
 * Se verifica si el usuario ha iniciado sesión antes de mostrar el formulario.
 *
 * @return Muestra el formulario de modificación de contraseña en la vista "funcionarios/modificar" si el usuario ha iniciado sesión.
 *         Si el usuario no ha iniciado sesión, se redirige a la página de inicio.
 */ 
    @GetMapping("/modificar")
    public String modifyForm(){
        // Verificacion de session
        if(!sessionUser.existSession()){
            return "redirect:/";
        }        
        return "funcionarios/modificar";
    }
    
    /**
 * Controlador para procesar el formulario de modificación de contraseña.
 *
 * Esta ruta permite a un usuario cambiar su contraseña a través de un formulario de modificación.
 * Se verifica si el usuario ha iniciado sesión antes de procesar la modificación de la contraseña.
 *
 * @param contraseña   La nueva contraseña proporcionada por el usuario.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Redirige a la página de inicio con un mensaje de modificación exitosa si se cambia la contraseña correctamente.
 *  Si el usuario no ha iniciado sesión, se redirige a la página de inicio.
 */
    @PostMapping("/modificar")
    public String processModifyForm(@RequestParam("contraseña") String contraseña,RedirectAttributes atributosMensaje){
        
        // Verificacion de session
        if(!sessionUser.existSession()){
            return "redirect:/";
        }
        
        // Cambio la contrseña del funcionario que inicio session
        sessionUser.getFuncionario().setContraseña(contraseña);
        this.funcionarioServices.crearFuncionario(sessionUser.getFuncionario());
        
        //Una vez modificado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Cambios modificados correctamente");
        return "redirect:/";
    }  
}
