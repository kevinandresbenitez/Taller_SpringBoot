/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;
import com.proyect.models.Administrador;
import com.proyect.models.Funcionario;
import com.proyect.services.AdministradorService;
import com.proyect.services.FuncionarioService;
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
@RequestMapping("/administradores")
public class AdministradorController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private AdministradorService administradorService;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<Administrador> administradores = this.administradorService.listarAdministradores();
        modelo.addAttribute("administradores",administradores);
        return "administradores/index";
    }
    
    @GetMapping("/asignarFuncionario")
    public String assing(Model model){
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        model.addAttribute("funcionarios",funcionarios);
        return "administradores/asignarFuncionario";
    }
        
    @GetMapping("/asignarFuncionario/{id}")
    public String processAssing(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        Boolean esAdministrador = this.administradorService.esFuncionarioAdministrador(id);
        
        // Si el funcionario no existe o ya es un administrador
        if(funcionario.isEmpty() || esAdministrador){
            atributosMensaje.addFlashAttribute("mensaje","No se puede agregar como administrador, ya que el funcionario no existe o ya es administrador");
            return "redirect:/administradores/";
        }

        this.administradorService.asignarFuncionarioComoAministrador(funcionario.get());
        atributosMensaje.addFlashAttribute("mensaje","Se a asignado adecuadamente al funcionario como administrador");
        return "redirect:/administradores/";
    }
        
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        this.administradorService.eliminarAdministradorPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se a revocado la administracion del funcionario");
        return "redirect:/administradores/";
    }
        
}
