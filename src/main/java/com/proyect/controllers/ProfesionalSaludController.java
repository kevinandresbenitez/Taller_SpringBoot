/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;
import com.proyect.models.Funcionario;
import com.proyect.models.ProfesionalSalud;
import com.proyect.services.FuncionarioService;
import com.proyect.services.ProfesionalSaludService;
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
@RequestMapping("/profesionalSalud")
public class ProfesionalSaludController {
    @Autowired
    private ProfesionalSaludService profesionalSaludService;
    @Autowired
    private FuncionarioService funcionarioServices;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<ProfesionalSalud> profesionalesSalud = this.profesionalSaludService.listarProfesionalSalud();
        modelo.addAttribute("profesionalesSalud",profesionalesSalud);
        return "profesionalesSalud/index";
    }
    
    @GetMapping("/asignarFuncionario")
    public String selectFuncionary(Model model){
        List<Funcionario> funcionarios = this.funcionarioServices.listarFuncionarios();
        model.addAttribute("funcionarios",funcionarios);
        return "profesionalesSalud/mostrarFuncionarios";
    }
    
    @GetMapping("/asignarFuncionario/{id}")
    public String showForm(@PathVariable("id") Long id, Model model){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id);
        
        if(funcionario.isEmpty()){
            return "redirect:/profesionalSalud/";
        }
        model.addAttribute("funcionario",funcionario.get());
        return "profesionalesSalud/asignarFuncionario";
    }
    
    @PostMapping("/asignarFuncionario/{id}")
    public String processAssing(@PathVariable("id") Long id,@RequestParam(name = "nroMatricula") Long nroMatricula){
        Boolean funcionarioEsProfesionalSalud = this.profesionalSaludService.esFuncionarioProfesionalSaludPorId(id);
        Optional<Funcionario> funcionarioExistente = this.funcionarioServices.obtenerFuncionarioPorId(id);
                
        // Si el funcionario no existe o ya es un profesional de la salud
        if(funcionarioEsProfesionalSalud || funcionarioExistente.isEmpty()){
            return "redirect:/profesionalSalud/";
        }
        
        this.profesionalSaludService.asignarFuncionarioComoProfesionalSalud(funcionarioExistente.get(),nroMatricula);  
        return "redirect:/profesionalSalud/";
    }
        
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable("id") Long id){
        this.profesionalSaludService.eliminarProfesionalSaludPorId(id);
        return "redirect:/profesionalSalud/";
    }
}
