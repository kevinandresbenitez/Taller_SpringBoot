package com.proyect.controllers.paciente;

import com.proyect.models.*;
import com.proyect.services.*;
import com.proyect.session.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes/consultas")
public class ConsultaController {
    @Autowired
    ConsultaService consultaService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    ResultadoEstudioService resultadoEstudioService;
    @Autowired
    MedicoService medicoService;

    @Autowired
    BoxService boxService;
    
    @Autowired
    SessionUsuario sessionUser;

    /**
     *
     * @param model se utiliza para poder pasarle variables a los modelos de vistas
     * @param id el id del paciente es usado para poder encontrarlo en el sistema
     * @return vista con la lista de consultas del paciente
     */
    @GetMapping("/{id}")
    public String listaConsultas(Model model, @PathVariable("id") Long id) {
        // Verificacion de session
        if(!sessionUser.existSession() || !(sessionUser.isMedic() || sessionUser.hashRol("Administrativo"))){
            return "redirect:/";
        }
        
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (!paciente.isPresent()) {
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/consultas/index";
    }

    /**
     *
     * @param model se utiliza para poder pasarle variables a los modelos de vistas
     * @param id el id del paciente es usado para poder ubicar en el sistema
     * @return la vista del formulario para poder agregar una consulta
     */
    @GetMapping("/crear/{id}")
    public String crearConsultas(Model model,@PathVariable("id") Long id){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }        

        Optional<Paciente> paciente = pacienteService.obtenerPacienteById(id);

        if(paciente.isEmpty()){
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente",paciente.get());
        return "/pacientes/consultas/crear";
    }

    /**
     *
     * @param diagnostico diagnostico de un paciente
     * @param tipoAtencion el tipo de atencion
     * @param diagnosticosClinicos diagnostico clinico
     * @param id id de un paciente para ubicarlo en el sistema
     * @param redirectAttributes se utiliza para manejar que solo podamos agregar resultados de estudios una sola vez
     * @return se redirije a la vista de agregar resultados de estudios
     *          con el id de su respectiva consulta
     */
    @PostMapping("/crear/{id}")
    public String crearConsultas(@RequestParam("diagnostico") String diagnostico,
                                 @RequestParam("tipoAtencion") String tipoAtencion,
                                 @RequestParam("diagnosticosClinicos") String diagnosticosClinicos,
                                 @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }        
        
        Optional<Paciente> paciente = pacienteService.obtenerPacienteById(id);
        
        Consulta consulta = new Consulta();
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        
        //Agregando atributos de la consulta        
        consulta.setMedico(this.sessionUser.getMedic());
        consulta.setHoraAtencion(tiempohoy);
        consulta.setFechaAtencion(fechahoy);
        consulta.setTipoAtencion(tipoAtencion);
        consulta.setDiagnostico(diagnostico);
        consulta.setDiagnosticosClinicos(diagnosticosClinicos);
        consulta.setPaciente(paciente.get());
        consulta.setIngreso(paciente.get().getIngresos().get(paciente.get().getIngresos().size()-1));
        consulta.setTriage(paciente.get().getTriages().get(paciente.get().getTriages().size()-1));
        consultaService.guardarConsulta(consulta);
        
        //Vaciando el box en el que estaba el paciente  atendiendose
        Box box = boxService.findByPacienteId(paciente.get().getId());
        box.setPaciente(null);
        boxService.guardarBox(box);
        
        // Agregando flash para solo agregar resultados de estudios 1 vez
        String permitirCreacionResEst = "si";
        redirectAttributes.addFlashAttribute("PermitirCreacionResEst", permitirCreacionResEst);
        return "redirect:/pacientes/consultas/agregarresultadosestudios/"+consulta.getId();
    }

    /**
     *
     * @param model se utiliza para poder pasarle variables a los modelos de vistas
     * @param id id para poder ubicar a que consulta se le agregaran los resultados de estudios
     * @param PermitirCreacionResEst es la bandera que nos verifica si ya pasó el momento de agregar
     *                               los resultados de estudios
     * @param atributos es un atributo flash, puede utilizarse una vez y en este momento
     *                           es usado para verificar que se pueda agregar resultados de estudios
     * @return vista del formulario para agregar resultados estudios
     */
    @GetMapping("/agregarresultadosestudios/{id}")
    public String formularioResultadoEstudios(Model model,@PathVariable("id")Long id
            ,@ModelAttribute("PermitirCreacionResEst") String PermitirCreacionResEst
            ,RedirectAttributes atributos){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }        
        
        Optional<Consulta> consulta = consultaService.obtenerConsultaPorId(id);                
        // Si la consulta no existe o si la consulta es vieja( no se modifica )
        if (!(PermitirCreacionResEst == "si") || consulta.isEmpty()) {
            atributos.addFlashAttribute("mensaje","La consulta no existe o no esta disponible su modificacion");
            return "redirect:/";
        }

        // Agregando flash para solo agregar resultados de estudios 1 vez
        atributos.addFlashAttribute("PermitirCreacionResEst", "si");
        model.addAttribute("consulta",consulta.get());
        return "pacientes/consultas/resultadosestudios/agregar";
    }


    /**
     *
     * @param tiposInformes lista de tipos de informes realizados a un paciente en su respectiva consulta
     * @param informesEstudios lista de informes de estudios realizados a un paciente en su respectiva consulta
     * @param atributosMensaje Atributos para poder redirijir con un mensaje
     * @param id id para poder ubicar a que consulta se le agregaran los resultados de estudios
     * @return vista de los boxes de atencion
     */
    @PostMapping("/agregarresultadoestudios/{id}")
    public String formularioResultadosEstudios(@RequestParam(value = "tipoInforme[]", required = false) List<String> tiposInformes,
                                               @RequestParam(value = "informeEstudio[]", required = false) List<String> informesEstudios,
                                               @PathVariable("id")Long id,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isMedicalSpecialist()){
            return "redirect:/";
        }        

        Optional<Consulta> consulta = consultaService.obtenerConsultaPorId(id);        
        // Si no existe la consulta
        if(consulta.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","La consulta no existe");
            return "redirect:/";
        }
        
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        for(int i=0;i<tiposInformes.size();i++){
            ResultadoEstudio resultadoEstudio = new ResultadoEstudio();
            resultadoEstudio.setHora(tiempohoy);
            resultadoEstudio.setFecha(fechahoy);
            resultadoEstudio.setTipoInforme(tiposInformes.get(i));
            resultadoEstudio.setInformeEstudio(informesEstudios.get(i));
            resultadoEstudio.setPaciente(consulta.get().getPaciente());
            resultadoEstudioService.guardarResultadoEstudio(resultadoEstudio);
            consulta.get().getResultadoEstudios().add(resultadoEstudio);

        }
        atributosMensaje.addFlashAttribute("mensaje","Consulta Agregada exitosamente");
        consultaService.guardarConsulta(consulta.get());
        return "redirect:/pacientes/atenciones/";
    }

    // Resultados de esutudios que estan en la consulta

    /**
     *
     * @param model se utiliza para pasar variables a la vista
     * @param id id para ubicar la consulta en el sistema
     * @param atributos Atributos para redirijr con un mensaje
     * @return vista para mostrar listado de resultado de estudios si la encuentra, si no redirije a / con un mensaje
     */
    @GetMapping("/resultadosestudios/{id}")
    public String listaResultadosEstudios(Model model, @PathVariable("id") Long id,RedirectAttributes atributos) {
        // Verificacion de session
        if(!sessionUser.existSession() || !(sessionUser.isMedic() || sessionUser.hashRol("Administrativo"))){
            return "redirect:/";
        }
        
        Optional<Consulta> consulta = consultaService.obtenerConsultaPorId(id);
        if (consulta.isEmpty()) {
            atributos.addFlashAttribute("mensaje","La consulta no existe");
            return "redirect:/";
        }
        model.addAttribute("consulta", consulta.get());
        return "pacientes/consultas/resultadosestudios/index";
    }
    
}
