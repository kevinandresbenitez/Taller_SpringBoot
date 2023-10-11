/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud;
import com.proyect.models.Enfermero;
import com.proyect.models.Funcionario;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.services.AdministradorService;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
import com.proyect.services.ProfesionalSaludService;
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
@RequestMapping("/profesionalSalud/medicos")
public class MedicoController {
    @Autowired
    private ProfesionalSaludService profesionalSaludService;
    @Autowired
    private EnfermeroService enfermeroService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private AdministradorService administradorService;
        
    
    @GetMapping("/")
    public String listMedicos(Model model){
        List<Medico> medicos = this.medicoService.listarEnfermeros();
        model.addAttribute("medicos",medicos);
        return "profesionalesSalud/medicos/index";
    }
    
    @GetMapping("/asignar/")
    public String asignarMedico(Model model){
        List<ProfesionalSalud> profSalud = this.profesionalSaludService.listarProfesionalSalud();
        model.addAttribute("profesionalesSalud",profSalud);
        return "profesionalesSalud/medicos/asignar";
    }
    
    @GetMapping("/asignar/{id}")
    public String procesarAsignacionMedico(@PathVariable("id") Long id){
        return "redirect:/medicos/";        
    }
    
}
