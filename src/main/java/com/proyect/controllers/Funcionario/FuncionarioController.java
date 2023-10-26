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
 * @author kevin
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
    
    @GetMapping("/crear")
    public String createForm(){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }        
        return "funcionarios/crear";
    }
    
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
        
    
    @GetMapping("/modificar")
    public String modifyForm(){
        // Verificacion de session
        if(!sessionUser.existSession()){
            return "redirect:/";
        }        
        return "funcionarios/modificar";
    }
    
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
