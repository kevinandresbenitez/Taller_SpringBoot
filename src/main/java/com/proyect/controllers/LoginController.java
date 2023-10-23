/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;

import com.proyect.models.Box;
import com.proyect.models.Funcionario;
import com.proyect.services.BoxService;
import com.proyect.services.FuncionarioService;
import com.proyect.session.SessionUsuario;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author eduardo
 */
@Controller
@Setter
@Getter
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SessionUsuario sessionUser;
    
    @Autowired
    private FuncionarioService funcionarioService;
    
    
      @GetMapping("/")      
      public String inicio(Model model) {
        return "login/iniciarSesion";
      }
    
      
      @PostMapping("/iniciarSesion")      
      public String verificarFuncionario(@RequestParam("correo") String correo,@RequestParam("contraseña") String contraseña,RedirectAttributes atri){

        Funcionario funcionario =this.funcionarioService.buscarFuncionarioPorCorreoYContraseña(correo, contraseña);
        
        if(funcionario == null){
            atri.addFlashAttribute("mensaje","no existe el funcionario");
            return"redirect:/login/";
        }
        
        sessionUser.startSession(funcionario);
        atri.addFlashAttribute("mensaje","iniciando session correctamente");
        return "redirect:/";
      }
      
      @GetMapping("/cerrarSesion")      
      public String cerrarSession(Model model){

        
        return "boxes/index";
      }
      
      
}
