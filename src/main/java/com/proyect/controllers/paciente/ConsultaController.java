package com.proyect.controllers.paciente;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.ConsultaService;
import com.proyect.services.MedicoService;
import com.proyect.services.PacienteService;
import com.proyect.services.ResultadoEstudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String listaConsultas(Model model, @PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (!paciente.isPresent()) {
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente", paciente.get());
        return "pacientes/consultas/index";
    }
    @GetMapping("/crear/{id}")
    public String crearConsultas(Model model,@PathVariable("id") Long id){

        Paciente paciente = pacienteService.obtenerPacienteById(id);

        if(paciente == null){
            return "redirect:/pacientes/";
        }
        model.addAttribute("paciente",paciente);
        return "/pacientes/consultas/crear";
    }

    @PostMapping("/crear/{id}")
    public String crearConsultas(@RequestParam("diagnostico") String diagnostico,
                                 @RequestParam("tipoAtencion") String tipoAtencion,
                                 @RequestParam("diagnosticosClinicos") String diagnosticosClinicos,
                                 @PathVariable("id") Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Consulta consulta = new Consulta();
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        List<Medico> medicos = medicoService.listarMedicos();
        Medico medico = medicos.get(medicos.size() -1 );
        consulta.setMedico(medico);
        consulta.setHoraAtencion(tiempohoy);
        consulta.setFechaAtencion(fechahoy);
        consulta.setTipoAtencion(tipoAtencion);
        consulta.setDiagnostico(diagnostico);
        consulta.setDiagnosticosClinicos(diagnosticosClinicos);
        consulta.setPaciente(paciente);
        consulta.setIngreso(paciente.getIngresos().get(paciente.getIngresos().size()-1));
        consulta.setTriage(paciente.getTriages().get(paciente.getTriages().size()-1));
        consultaService.guardarConsulta(consulta);
        pacienteService.guardarPaciente(paciente);

        return "redirect:/pacientes/consultas/agregarresultadosestudios/"+consulta.getId();
    }

    @GetMapping("/resultadosestudios/{id}")
    public String listaResultadosEstudios(Model model, @PathVariable("id") Long id) {
        Consulta consulta = consultaService.obtenerConsultaPorId(id);
        if (consulta == null) {
            return "redirect:/pacientes/consultas/";
        }
        model.addAttribute("consulta", consulta);
        return "pacientes/consultas/resultadosestudios/index";
    }

    @GetMapping("/agregarresultadosestudios/{id}")
    public String formularioResultadoEstudios(Model model,@PathVariable("id")Long id){
        Consulta consulta = consultaService.obtenerConsultaPorId(id);
        model.addAttribute("consulta",consulta);
        return "pacientes/consultas/resultadosestudios/agregar";
    }

    @PostMapping("/agregarresultadoestudios/{id}")
    public String formularioResultadosEstudios(@RequestParam(value = "tipoInforme[]", required = false) List<String> tiposInformes,
                                               @RequestParam(value = "informeEstudio[]", required = false) List<String> informesEstudios,
                                               @PathVariable("id")Long id){
        Consulta consulta = consultaService.obtenerConsultaPorId(id);
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        for(int i=0;i<tiposInformes.size()-1;i++){
            ResultadoEstudio resultadoEstudio = new ResultadoEstudio();
            resultadoEstudio.setHora(tiempohoy);
            resultadoEstudio.setFecha(fechahoy);
            resultadoEstudio.setTipoInforme(tiposInformes.get(i));
            resultadoEstudio.setInformeEstudio(informesEstudios.get(i));
            resultadoEstudio.setConsulta(consulta);
            resultadoEstudio.setPaciente(consulta.getPaciente());
            resultadoEstudioService.guardarResultadoEstudio(resultadoEstudio);
        }
        consultaService.guardarConsulta(consulta);
        return "redirect:/pacientes/consultas/"+consulta.getPaciente().getId();
    }

}
