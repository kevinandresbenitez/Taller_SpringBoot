/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.paciente;

/**
 *
 * @author alvez
 */
import com.proyect.models.Consulta;
import com.proyect.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pacientes/consultas/resultadosestudios")
public class ResultadoEstudioController {
    @Autowired
    ConsultaService consultaService;


    @GetMapping("/resultadosestudios/{id}")
    public String listaResultadosEstudios(Model model, @PathVariable("id") Long id) {
        Consulta consulta = consultaService.obtenerConsultaPorId(id);
        if (consulta == null) {
            return "redirect:/";
        }
        model.addAttribute("consulta", consulta);
        return "pacientes/consultas/resultadosestudios";
    }
}
