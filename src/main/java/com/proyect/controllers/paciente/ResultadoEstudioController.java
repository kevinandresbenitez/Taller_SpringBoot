/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.paciente;

/**
 *
 * controlador para gestionar los resulltados de estudios
 */
import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.PacienteService;
import com.proyect.services.ResultadoEstudioService;
import com.proyect.session.SessionUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pacientes/resultadosestudios")
public class ResultadoEstudioController {
    @Autowired
    ResultadoEstudioService resultadoEstudioService;
    
    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    SessionUsuario sessionUser;
    
    /**
 * Controlador para mostrar los resultados de estudios de un paciente específico.
 *
 * @param model  se utilizará para agregar datos que se mostrarán en la vista.
 * @param id  id del paciente cuyos resultados de estudios se deben mostrar.
 * @return  muestra los resultados de estudios o redirige a la página de inicio si no se encuentra el paciente o si el usuario no tiene acceso.
 */
    // Resultados de esutudios del paciente
    @GetMapping("/{id}")
    public String listaResultadosEstudios(Model model, @PathVariable("id") Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        
        if(paciente == null) {
            return "redirect:/pacientes/";
        }
        
        model.addAttribute("resultadosEstudios", paciente.getResultadosEstudios());
        return "pacientes/resultadosEstudios/index";
    }
    

    
    
    
}
