package com.proyect.controllers.paciente;


import com.proyect.models.Paciente;
import com.proyect.models.Ingreso;
import com.proyect.services.PacienteService;
import com.proyect.services.IngresoService;
import com.proyect.session.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/pacientes/ingresos")
public class IngresoController {
    @Autowired
    IngresoService ingresoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    SessionUsuario sessionUser;
    
    @GetMapping("/verificarExistencia")
    public String formularioVerificarExistencia(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        return "pacientes/ingresos/verificar";
    }
    
    
    @PostMapping("/verificarExistencia")
    public String procesarVerificarExistencia(Model model,@RequestParam("dni")int dni, RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Optional<Paciente> paciente = pacienteService.findByDni(dni);
        
        if(paciente.isEmpty()){
            atributos.addAttribute("dni",dni);
            return "redirect:/pacientes/crear";
        }
        return "redirect:/pacientes/ingresos/agregar/"+paciente.get().getId();
    }

    
    
    @GetMapping("/agregar/{id}")
    public String formulario(Model model,@PathVariable("id")Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        model.addAttribute("paciente",paciente);
        return "pacientes/ingresos/agregar";
    }

    @PostMapping("/agregar/{id}")
    public String formulario(@PathVariable("id")Long id,
                             @RequestParam("motivoConsulta")String motivoConsulta,RedirectAttributes atribut){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        boolean tieneIngresoPrevio = this.pacienteService.estaElPacienteEnEspera(id);
        
        if(tieneIngresoPrevio){
            atribut.addFlashAttribute("mensaje","Este paciente ya esta esperando");
            return "redirect:/pacientes/ingresos/verificarExistencia";
        }
        
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Ingreso ingreso = new Ingreso();
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        ingreso.setFechaRegistro(fechahoy);
        ingreso.setHoraRegistro(tiempohoy);
        ingreso.setPaciente(paciente);
        ingreso.setMotivo(motivoConsulta);
        paciente.getIngresos().add(ingreso);
        ingresoService.guardarIngreso(ingreso);
        pacienteService.guardarPaciente(paciente);
        atribut.addFlashAttribute("mensaje","Paciente agregado correctamente");
        return "redirect:/";
    }



}
