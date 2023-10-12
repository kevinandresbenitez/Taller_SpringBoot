package com.proyect.controllers;

import com.proyect.models.ResultadoEstudio;
import com.proyect.services.ResultadoEstudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resultado-estudio")
public class ResultadoEstudioController {
    @Autowired
    private ResultadoEstudioService resultadoEstudioService;

    @GetMapping("/")
    public String listarResultados(Model model) {
        List<ResultadoEstudio> resultados = this.resultadoEstudioService.listarResultados();
        model.addAttribute("resultados", resultados);
        return "estudios/index";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        ResultadoEstudio nuevoEstudio = new ResultadoEstudio(); // Crea una nueva instancia para agregar
        model.addAttribute("resultadoEstudio", nuevoEstudio);
        return "estudios/formulario-agregar";
    }

    @PostMapping("/agregarEstudio")
    public String agregarEstudio(@ModelAttribute ResultadoEstudio resultadoEstudio) {
        resultadoEstudioService.agregarEstudio(resultadoEstudio);
        return "estudios/agregarEstudio";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable("id") Long id, Model model) {
        ResultadoEstudio resultadoEstudio = resultadoEstudioService.obtenerEstudioPorId(id);
        model.addAttribute("resultadoEstudio", resultadoEstudio);
        return "estudios/formulario-modificar";
    }

    @PostMapping("/modificar")
    public String modificarEstudio(@ModelAttribute ResultadoEstudio resultadoEstudio) {
        resultadoEstudioService.modificarEstudio(resultadoEstudio);
        return "redirect:/resultado-estudio/"; // Redirige a la lista de resultados
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEstudio(@PathVariable("id") Long id) {
        resultadoEstudioService.eliminarEstudio(id);

        return "estudios/eliminarEstudio";
    }
}
