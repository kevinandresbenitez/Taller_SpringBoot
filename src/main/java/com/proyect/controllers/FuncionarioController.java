/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;
import com.proyect.models.Funcionario;
import com.proyect.models.Sector;
import com.proyect.services.FuncionarioService;
import com.proyect.services.RolService;
import java.util.List;

import com.proyect.services.SectorService;
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
    public void FuncionarioController(FuncionarioService funcionarioServices,RolService rolServices){
        this.funcionarioServices = funcionarioServices;
        this.rolServices = rolServices;
    }
    
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
    public void propcessForm(@RequestParam("nombre") String nombre){
        Funcionario funcionario = new Funcionario();
        funcionario.setNombre(nombre);
        this.funcionarioServices.crearFuncionario(funcionario);
    }
    @GetMapping("/listasectores")
    public List<Sector> listaSectores(){
        return sectorService.listaSectores();
    }
}
