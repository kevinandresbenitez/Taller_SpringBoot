package com.proyect.controllers.paciente;

import com.proyect.models.*;
import com.proyect.repositories.TriageRepository;
import com.proyect.services.ConsultaService;
import com.proyect.services.PacienteService;
import com.proyect.services.IngresoService;
import com.proyect.services.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.proyect.repositories.IngresoRepository;
import com.proyect.session.SessionUsuario;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;
    @Autowired
    SessionUsuario sessionUser;

    /**
     *
     * @param model se usa para pasar el listado de pacientes a la vista
     * @return vista de los pacientes
     */

    @GetMapping("/")
    public String detailsPaciente(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        List<Paciente> pacientes = pacienteService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/index";
    }

    /**
     *
     * @param dni se lo pasa a la vista para rellenar el campo dni si no existe el paciente
     * @param modelo se usa para usar variables en las vistas
     * @return vista del formulario de registro de paciente
     */
    @GetMapping("/crear")
    public String mostrarForm(@RequestParam("dni") Optional<Integer> dni,Model modelo) {
        
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        
        if(dni.isPresent()){
            modelo.addAttribute("dni",dni.get());
        }
        
        return "/pacientes/crear";
    }

    /**
     *
     * @param nombre nombre y apellido del paciente
     * @param dni dni del paciente
     * @param email email del paciente
     * @param domicilio domicilio del paciente
     * @param telefonoCelular telefono del paciente
     * @param telefonoFijo telefono fijo del paciente
     * @param fechaNacimiento fecha de nacimiento del paciente
     * @param estadoCivil estado civil del paciente
     * @return vista para agregar un motivo de consulta
     */

    @PostMapping("/crear")
    public String registrarPaciente(@RequestParam("nombre") String nombre,
                                @RequestParam("dni") int dni,
                                @RequestParam("email") String email,
                                @RequestParam("domicilio") String domicilio,
                                @RequestParam("telefonoCelular") int telefonoCelular,
                                @RequestParam("telefonoFijo") int telefonoFijo,
                                @RequestParam("fechaNacimiento") LocalDate fechaNacimiento,
                                @RequestParam("estadoCivil") String estadoCivil) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Optional<Paciente> VerificarPaciente = pacienteService.findByDni(dni);
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setDni(dni);
        paciente.setTelefonoFijo(telefonoFijo);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setEmail(email);
        paciente.setTelefonoCelular(telefonoCelular);
        paciente.setDomicilio(domicilio);
        paciente.setEstadoCivil(estadoCivil);
        
                
        //Si esta creado ya, redirije a agregar ingreso
        if(VerificarPaciente.isPresent()){
            return "redirect:/pacientes/ingresos/agregar/"+VerificarPaciente.get().getId();
        }
        
        
        pacienteService.guardarPaciente(paciente);
        return "redirect:/pacientes/ingresos/agregar/"+paciente.getId();
    }

    /**
     *
     * @param dni buscamos un paciente usando su dni
     * @param model se utilza para pasar variables a la vista
     * @param mensaje se utiliza para poder mandar un mensaje a la vista en caso de no encontrar al paciente
     * @return
     */
    @GetMapping("/buscar")
    public String buscarPacientePorDNI(@RequestParam("dni") int dni, Model model,RedirectAttributes mensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.hashRol("Administrativo")){
            return "redirect:/";
        }
        
        Optional<Paciente> paciente = pacienteService.findByDni(dni);
        if (paciente.isEmpty()){
            mensaje.addFlashAttribute("mensaje","no se encontro al paciente");
            return "redirect:/pacientes/";
        }
        
        model.addAttribute("paciente", paciente.get());
        return "pacientes/index";
    }
}
