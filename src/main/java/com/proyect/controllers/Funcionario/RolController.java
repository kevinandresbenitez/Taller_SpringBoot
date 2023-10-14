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
@RequestMapping("/funcionarios/roles/")
public class RolController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private RolService rolServices;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<Rol> roles = this.rolServices.listarRoles();
        modelo.addAttribute("roles",roles);
        return "funcionarios/roles/index";
    }
    
    @GetMapping("/asignar/{id_funcionario}")
    public String asignarRoles(@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
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
    
    
    @PostMapping("/asignar/{id_funcionario}")
    public String procesarAsignacionRoles(@RequestParam("roles_id") List<Long> RolesId,@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        if(RolesId.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","necesita seleccionar al menoz un rol");
            return "redirect/funcionarios/";
        }
        
        
        
        //Si los roles existen los agrego al funcionario
        for (Long id_rol : RolesId) {
            Optional<Rol> rol = this.rolServices.obtenerPorId(id_rol);
            
            if(rol.isPresent()){
                // Al rol le setteo el funcionario
                rol.get().getFuncionarios().add(funcionario.get());
                // Al funcionario le setteo el rol y elimino los anteriores
                funcionario.get().getRoles().removeAll(funcionario.get().getRoles());
                funcionario.get().getRoles().add(rol.get());
                //Guardo
                this.rolServices.crear(rol.get());
                this.funcionarioServices.crearFuncionario(funcionario.get());
            }
            
        }
        
        atributosMensaje.addFlashAttribute("mensaje","se ha asignado el rol correctamente");
        return "redirect:/funcionarios/";
    }
    
    
    @GetMapping("/crear")
    public String createForm(){        
        return "funcionarios/roles/crear";
    }
    
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre,RedirectAttributes atributosMensaje){
        Rol rol = new Rol();
        rol.setNombre(nombre);
        this.rolServices.crear(rol);
        
        //Una vez creado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Se creo el rol Adecuadamente");
        return "redirect:/funcionarios/roles/";
    }
      
    @GetMapping("/eliminar/{id}")
    public String deleteFuncionary(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        this.rolServices.eliminarPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se ah eliminado el rol correctamente");
        return "redirect:/funcionarios/roles/";
    }
}
