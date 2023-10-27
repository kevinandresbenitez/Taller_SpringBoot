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
 * Controlador para gestionar roles de funcionarios
 */
@Controller
@Setter
@Getter
@RequestMapping("/funcionarios/roles/")
public class RolController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private RolService rolServices;
    @Autowired
    SessionUsuario sessionUser;
    
    
     /*
        * Controlador para listar roles de funcionarios
        *
        *  @param modelo Modelo utilizado para agregar atributos que serán utilizados en la vista.
         * @return Retorna la vista "funcionarios/index" con la lista de funcionarios si el usuario está autenticado.
         *  Si no, redirige al inicio.
         */
    @GetMapping("/")
    public String list(Model modelo){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Rol> roles = this.rolServices.listarRoles();
        modelo.addAttribute("roles",roles);
        return "funcionarios/roles/index";
    }
    
    /**
         * Controlador para asignar roles a un funcionario
         *
         * @param id_funcionario El ID del funcionario al que se asignarán los roles.
         * @param modelo Modelo utilizado para agregar atributos que serán utilizados en la vista.
         * @param atributosMensaje agrega mensajes que se mostrarán en la redirección.
         * @return Retorna la vista "funcionarios/roles/asignar" con la información del funcionario y
         *  la lista de roles disponibles si el usuario está autenticado. Si no, redirige al inicio o muestra un mensaje de error.
         */
    @GetMapping("/asignar/{id_funcionario}")
    public String asignarRoles(@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Rol> roles = this.rolServices.listarRoles();
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        modelo.addAttribute("funcionario",funcionario.get());
        modelo.addAttribute("roles",roles);
        return "funcionarios/roles/asignar";
    }
    
    /**
         * Controlador que permite asignar roles a un funcionario específico.
         *
         *
         * @param id_funcionario El ID del funcionario al que se le asignarán los roles.
         * @param modelo Objeto Modelo utilizado para agregar atributos que se utilizarán en la vista.
         * @param atributosMensaje agrega mensajes que se mostrarán después de la redirección.
         * @return Retorna la vista "funcionarios/roles/asignar" con información sobre el funcionario y la lista de roles disponibles,
         * si el usuario está autenticado. Si no, redirige al inicio o muestra un mensaje de error.
         */
    @PostMapping("/asignar/{id_funcionario}")
    public String procesarAsignacionRoles(@RequestParam("roles_id") List<Long> RolesId,@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        if(RolesId.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","necesita seleccionar al menoz un rol");
            return "redirect/funcionarios/";
        }
        
        
        // Remuevo los roles anteriores
        funcionario.get().getRoles().removeAll(funcionario.get().getRoles());
        
        
        //Si los roles existen los agrego al funcionario
        for (Long id_rol : RolesId) {
            Optional<Rol> rol = this.rolServices.obtenerPorId(id_rol);
            if(rol.isPresent()){
                rol.get().getFuncionarios().add(funcionario.get());
                funcionario.get().getRoles().add(rol.get());
                //Guardo
                this.rolServices.crear(rol.get());
                this.funcionarioServices.crearFuncionario(funcionario.get());
            }
            
        }
        
        atributosMensaje.addFlashAttribute("mensaje","se ha asignado el rol correctamente");
        return "redirect:/funcionarios/";
    }
    
     /**
         * Controlador para mostrar el formulario de creación de roles de funcionarios.
         *
         * @return Retorna la vista "funcionarios/roles/crear" que contiene el formulario de creación de roles de funcionarios,
         * si el usuario está autenticado. Si no, redirige al inicio.
         */
    @GetMapping("/crear")
    public String createForm(){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        return "funcionarios/roles/crear";
    }
    
    /**
         * Controlador para procesar la creación de un nuevo rol de funcionario.
         *
         *
         * @param nombre El nombre del nuevo rol, obtenido desde el formulario.
         * @param atributosMensaje  agrega mensajes que se mostrarán después de la redirección.
         * @return Retorna a la página de roles de funcionarios con un mensaje de creación exitosa si el rol se crea con éxito.
         * Si el usuario no ha iniciado sesión, se redirige a la página de inicio o muestra un mensaje de error si el rol ya existe.
         * 
         */
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }

        // Verificar si el rol con el mismo nombre ya existe
        if (rolServices.existsByNombre(nombre)) {
            atributosMensaje.addFlashAttribute("mensaje", "<span style='color: red;'>El rol ya existe!</span>");
            return "redirect:/funcionarios/roles/crear";
        }

        Rol rol = new Rol();
        rol.setNombre(nombre);

        this.rolServices.crear(rol);

        // Una vez creado, redirijo y envío un mensaje de creación exitosa
        atributosMensaje.addFlashAttribute("mensaje", "Se creó el rol adecuadamente");
        return "redirect:/funcionarios/roles/";
    }     
}
