/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;
import com.proyect.enums.TipoRol;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.services.AdministradorService;
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
    private AdministradorService administradorService;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        modelo.addAttribute("funcionarios",funcionarios);
        return "funcionarios/index";
    }
    
    @GetMapping("/crear")
    public String createForm(){        
        return "funcionarios/crear";
    }
    
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre){
        Funcionario funcionario = new Funcionario();
        funcionario.setNombre(nombre);
        this.funcionarioServices.crearFuncionario(funcionario);
        //Una vez creado redirijo
        return "redirect:/funcionarios/";
    }
    
    @GetMapping("/modificar/{id}")
    public String modify(Model modelo,@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        //Si no existe el funcionario redirijir a funcionario
        if(!funcionario.isPresent()){
            return "redirect:/funcionarios/";
        }
        
        modelo.addAttribute("funcionario",funcionario.get());
        return "funcionarios/modificar";
    }
    
    @GetMapping("/modificar/roles/{id}")
    public String modifyRol(Model modelo,@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        //Si no existe el funcionario redirijir a funcionario
        if(!funcionario.isPresent()){
            return "redirect:/funcionarios/";
        }
        //Envio informacion del usuario y la enum de roles
        
        modelo.addAttribute("roles",TipoRol.values());
        modelo.addAttribute("funcionario",funcionario.get());
        return "funcionarios/modificarRoles";
    }
    
    @PostMapping("/modificar/roles/{id}")
    public String processFormModifyRol(@PathVariable("id") Long id,@RequestParam(name = "rol",required = false) List<TipoRol> rolesEnviados){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        List<Rol> roles =new ArrayList<>();
        
        //Si no existe el funcionario y no hay roles
        if(rolesEnviados == null || funcionario.isEmpty()){
            return "redirect:/funcionarios/";
        }
        
        // Creando una lsta de roles
        for(int i=0;rolesEnviados.size() > i;i++){
            Rol rolAuxiliar = new Rol();
            rolAuxiliar.setRol(rolesEnviados.get(i));
            roles.add(rolAuxiliar);
        }
        
        //Agregando los nuevos roles
        this.administradorService.modificarRol(funcionario.get(),roles);
        
        //Redirijir al final */
        return "redirect:/funcionarios/";
    }
}
