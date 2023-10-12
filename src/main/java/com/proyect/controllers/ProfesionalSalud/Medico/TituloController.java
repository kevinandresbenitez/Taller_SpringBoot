/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.ProfesionalSalud.Medico;
import com.proyect.models.Especialidad;
import com.proyect.models.Medico;
import com.proyect.models.Titulo;
import com.proyect.services.EspecialidadService;
import com.proyect.services.TituloService;
import com.proyect.services.MedicoService;
import java.time.LocalDate;
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
@RequestMapping("/profesionalSalud/medicos/titulaciones")
public class TituloController {
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private TituloService tituloService;
    
    @GetMapping("/{id_medico}")
    public String listarTitulacionesDelMedico(Model model,@PathVariable("id_medico")Long id,RedirectAttributes atributosMensaje){
        Optional<Medico> medico = this.medicoService.obtenerMedicoPorId(id);
        
        if(medico.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el medico");
            return "redirect:/profesionalSalud/medicos/";
        }
        model.addAttribute("medico",medico.get());
        return "profesionalesSalud/medicos/titulaciones/index";
    }
    
    @GetMapping("/eliminar/{id_medico}/{id}")
    public String eliminarTitulacion(Model model,@PathVariable("id_medico")Long idMedico,@PathVariable("id")Long id,RedirectAttributes atributosMensaje){
        this.tituloService.eliminarTituloPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","La titulacion se elimino correctamente");
        return "redirect:/profesionalSalud/medicos/titulaciones/"+ idMedico;
    }
    
    @GetMapping("/crear/{id_medico}")
    public String formularioTitulacion(Model model,@PathVariable("id_medico")Long id){
        List<Especialidad> especialidades = this.especialidadService.listarEspecialidades();
        

        model.addAttribute("id_medico",id);
        model.addAttribute("especialidades",especialidades);
        return "profesionalesSalud/medicos/titulaciones/crear";
    }
    
    @PostMapping("/crear/{id_medico}")
    public String procesarCrearTitulacion(
            @RequestParam("id_especialidad") Long idEspecialidad,@PathVariable("id_medico") Long id,RedirectAttributes atributosMensaje,
            @RequestParam("fechaTitulacion") LocalDate fechaTitulacion,@RequestParam("universidad")String universidad){
        
        Optional<Especialidad> especialidad = this.especialidadService.obtenerEspecialidadPorId(idEspecialidad);
        Optional<Medico> medico = this.medicoService.obtenerMedicoPorId(id);
                
        if(especialidad.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","La especialidad seleccionada no existe");
            return "redirect:/profesionalSalud/medicos/titulaciones/" + id;
        }
        if(medico.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","El medico no existe");
            return "redirect:/profesionalSalud/medicos/" + id;
        }
        
        // Creando titulacion
        Titulo titulacion = new Titulo();
        titulacion.setEspecialidad(especialidad.get());
        titulacion.setMedico(medico.get());
        titulacion.setUniversidad(universidad);
        titulacion.setFechaGraduacion(fechaTitulacion);
        this.tituloService.crearTitulo(titulacion);
        atributosMensaje.addFlashAttribute("mensaje","La titulacion creo correctamente");
        return "redirect:/profesionalSalud/medicos/titulaciones/"+id;
    }
   
}