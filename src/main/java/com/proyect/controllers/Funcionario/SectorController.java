/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.Funcionario;

import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.models.Sector;
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
@RequestMapping("/funcionarios/sectores/")
public class SectorController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private SectorService sectorServices;
        
    @GetMapping("/")
    public String list(Model modelo){
        List<Sector> sectores = this.sectorServices.listarSectores();
        modelo.addAttribute("sectores",sectores);
        return "funcionarios/sectores/index";
    }
    
    @GetMapping("/asignar/{id_funcionario}")
    public String asignarRoles(@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        List<Sector> sectores = this.sectorServices.listarSectores();
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        modelo.addAttribute("funcionario",funcionario.get());
        modelo.addAttribute("sectores",sectores);
        return "funcionarios/sectores/asignar";
    }
    
    
    @PostMapping("/asignar/{id_funcionario}")
    public String procesarAsignacionRoles(@RequestParam("sectores_id") List<Long> SectoresId,@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        if(SectoresId.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","necesita seleccionar al menoz un sector");
            return "redirect/funcionarios/";
        }
        
        
        // Remuevo los Sectores anteriores
        funcionario.get().getSectores().removeAll(funcionario.get().getSectores());
        
        
        //Si los roles existen los agrego al funcionario
        for (Long id_sector : SectoresId) {
            Optional<Sector> sector = this.sectorServices.obtenerPorId(id_sector);
            if(sector.isPresent()){
                sector.get().getFuncionarios().add(funcionario.get());
                funcionario.get().getSectores().add(sector.get());
                //Guardo
                this.sectorServices.crear(sector.get());
                this.funcionarioServices.crearFuncionario(funcionario.get());
            }
            
        }
        
        atributosMensaje.addFlashAttribute("mensaje","se ha asignado el Sector correctamente");
        return "redirect:/funcionarios/";
    }
    
    
    @GetMapping("/crear")
    public String createForm(){        
        return "funcionarios/sectores/crear";
    }
    
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre,RedirectAttributes atributosMensaje){
        Sector sector = new Sector();
        sector.setNombre(nombre);
        this.sectorServices.crear(sector);
        
        //Una vez creado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Se creo el sector Adecuadamente");
        return "redirect:/funcionarios/sectores/";
    }
      
    @GetMapping("/eliminar/{id}")
    public String deleteFuncionary(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        this.sectorServices.eliminarPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se ah eliminado el sector correctamente");
        return "redirect:/funcionarios/sectores/";
    }
}
