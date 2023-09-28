/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.services.AdministradorService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.RolService;
import com.proyect.services.SectorService;
import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author kevin
 */
@Controller
@Setter
@Getter
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private RolService rolServices;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private AdministradorService administradorService;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        modelo.addAttribute("funcionarios",funcionarios);
        return "funcionario/index";
    }
    
    @GetMapping("/crear")
    public String createForm(){        
        return "funcionario/crear";
    }
    
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre){
        Funcionario funcionario = new Funcionario();
        funcionario.setNombre(nombre);
        this.funcionarioServices.crearFuncionario(funcionario);
        //Una vez creado redirijo
        return "redirect:/funcionario/";
    }
    
    @GetMapping("/modificar/{id}")
    public String modify(Model modelo,@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        //Si no existe el funcionario redirijir a funcionario
        if(!funcionario.isPresent()){
            return "redirect:/funcionario/";
        }
        
        modelo.addAttribute("funcionario",funcionario.get());
        return "funcionario/modificar";
    }
    
    @GetMapping("/modificar/roles/{id}")
    public String modifyRol(Model modelo,@PathVariable("id") Long id){
        return "funcionario/modificarRoles";
    }
    
    @PutMapping("/modificar/roles/{id}")
    public String processFormModifyRol(@PathVariable("id") Long id,@RequestParam List<Rol> rol){
        //"Procesar formulario para agregar roles"
        // procesando...
        //
        
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        if(funcionario.isPresent()){
            this.administradorService.modificarRol(funcionario.get(), rol);
        }
        
        //Redirijir al final
        return "redirect:/funcionario/";
    }
}
