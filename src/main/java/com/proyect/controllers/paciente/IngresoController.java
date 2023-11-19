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

/**
 *  en esta clase se realizan las operaciones con respecto a un ingreso de un paciente y el motivo de consulta
 */

@Controller
@RequestMapping("/pacientes/ingresos")
public class IngresoController {
    @Autowired
    IngresoService ingresoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    SessionUsuario sessionUser;

    /**
     *
     * @return si un funcionario no inicio sesion o no tiene rol administrativo es redireccionado a la pagina principal
     *          o al login
     *          vista con el formulario para verificar existencia en el sistema por dni
     */
    @GetMapping("/verificarExistencia")
    public String formularioVerificarExistencia() {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        return "pacientes/ingresos/verificar";
    }

    /**
     *
     * @param dni dni de un paciente para verificar que ya se encuentre registrado en el sistema
     * @param atributos si no se encuentra registrado este parametro hace posible poder concatenarlo en
     *                  el formulario de registro, en el campo dni
     * @return si un funcionario no inicio sesion o no tiene rol administrativo es redireccionado a la pagina principal
     *          o al login
     *          si el paciente no esta registrado es redireccionado al formulario de registro, caso contrario
     *          es enviado a rellenar el motivo de consulta
     */
    @PostMapping("/verificarExistencia")
    public String procesarVerificarExistencia(@RequestParam("dni")int dni, RedirectAttributes atributos) {
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

    /**
     *
     * @param model parametro que hace posible poder enviar variables del metodo a la vista
     * @param id id para ubicar un paciente en el sistema
     * @return si un funcionario no inicio sesion o no tiene rol administrativo es redireccionado a la pagina principal
     *          o al login, caso contrario se redirecciona al formulario para agregar un motivo de consulta
     *
     */
    
    @GetMapping("/agregar/{id}")
    public String formulario(Model model,@PathVariable("id")Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Optional<Paciente> paciente = pacienteService.obtenerPacienteById(id);
        model.addAttribute("paciente",paciente.get());
        return "pacientes/ingresos/agregar";
    }

    /**
     *
     * @param id id para ubicar al paciente en el sistema, ademas para poder verificar si se encuentra ya en espera de atencion
     * @param motivoConsulta motivo por el cual el paciente acude a atencion medica
     * @param atribut parametro que nos posibilita comunicarme con la vista y enviar un mensaje segun un evento
     * @return se retorna a la pagina principal
     *
     */
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
            return "redirect:/";
        }
        
        Optional<Paciente> paciente = pacienteService.obtenerPacienteById(id);
        Ingreso ingreso = new Ingreso();
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        ingreso.setFechaRegistro(fechahoy);
        ingreso.setHoraRegistro(tiempohoy);
        ingreso.setPaciente(paciente.get());
        ingreso.setMotivo(motivoConsulta);
        paciente.get().getIngresos().add(ingreso);
        ingresoService.guardarIngreso(ingreso);
        pacienteService.guardarPaciente(paciente.get());
        atribut.addFlashAttribute("mensaje","Paciente agregado correctamente");
        return "redirect:/";
    }



}
