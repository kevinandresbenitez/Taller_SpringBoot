/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.Funcionario;
import com.proyect.enums.TipoRol;
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
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private RolService rolServices;
    @Autowired
    private SectorService sectorService;
        
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
    public String processFormCreation(@RequestParam("nombre") String nombre,RedirectAttributes atributosMensaje){
        Funcionario funcionario = new Funcionario();
        funcionario.setNombre(nombre);
        this.funcionarioServices.crearFuncionario(funcionario);
        
        //Una vez creado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Se creo un funcionario Adecuadamente");
        return "redirect:/funcionarios/";
    }    
        
    @GetMapping("/eliminar/{id}")
    public String deleteFuncionary(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        this.funcionarioServices.eliminarFuncionarioPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Eliminando funcionario adecuadamente");
        return "redirect:/funcionarios/";
    }
}
