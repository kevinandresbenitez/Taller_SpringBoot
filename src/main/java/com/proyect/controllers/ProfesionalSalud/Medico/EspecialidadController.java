/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud.Medico;
import com.proyect.models.Especialidad;
import com.proyect.models.Medico;
import com.proyect.services.EspecialidadService;
import com.proyect.services.MedicoService;
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
@RequestMapping("/profesionalSalud/medicos/especialidades")
public class EspecialidadController {
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private MedicoService medicoService;
    
    @GetMapping("/")
    public String listarEspecializaciones(Model model){
        List<Especialidad> Especialidades = this.especialidadService.listarEspecialidades();
        model.addAttribute("especialidades",Especialidades);
        return "profesionalesSalud/medicos/especialidades/index";
    }
    
    @GetMapping("/crear/")
    public String crearEspecialidad(){
        return "profesionalesSalud/medicos/especialidades/crear";
    }
    
    @PostMapping("/crear/")
    public String procesarCreacion(RedirectAttributes atributosMensaje,@RequestParam("nombre") String nombre){
        // Creamos La Especialidad
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre);
        this.especialidadService.crearEspecialidad(especialidad);
        
        atributosMensaje.addFlashAttribute("mensaje","Se creo la especialidad correctamente");
        return "redirect:/profesionalSalud/medicos/especialidades/";
    }
    
    @GetMapping("/eliminar/{id}")
    public String procesarCreacion(RedirectAttributes atributosMensaje,@PathVariable Long id){
        this.especialidadService.eliminarEspecialidadPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se elimino la especialidad correctamente");
        return "redirect:/profesionalSalud/medicos/especialidades/";
    }

}