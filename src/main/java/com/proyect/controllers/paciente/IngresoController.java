package com.proyect.controllers.paciente;


import com.proyect.models.Paciente;
import com.proyect.models.Ingreso;
import com.proyect.services.PacienteService;
import com.proyect.services.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Controller
@RequestMapping("/pacientes/ingresos")
public class IngresoController {
    @Autowired
    IngresoService registroService;
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/{id}")
    public String formulario(Model model,@PathVariable("id")Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        model.addAttribute("paciente",paciente);
        return "pacientes/ingresos/crear";
    }

    @PostMapping("/{id}")
    public String formulario(@PathVariable("id")Long id,
                             @RequestParam("motivoConsulta")String motivoConsulta){
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Ingreso registro = new Ingreso();
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        registro.setFechaRegistro(fechahoy);
        registro.setHoraRegistro(tiempohoy);
        registro.setPaciente(paciente);
        registro.setMotivo(motivoConsulta);
        paciente.setRegistro(registro);
        registroService.guardarRegistro(registro);
        pacienteService.crearPaciente(paciente);
        return "redirect:/";
    }



}
