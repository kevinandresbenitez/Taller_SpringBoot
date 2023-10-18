package com.proyect.controllers.paciente;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.ConsultaService;
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
    public String crearConsultas(@RequestParam(value = "tipoInforme[]", required = false) List<String> tiposInformes,
                                 @RequestParam(value = "informeEstudio[]", required = false) List<String> informesEstudios,
                                 @RequestParam("diagnostico") String diagnostico,
                                 @RequestParam("tipoAtencion") String tipoAtencion,
                                 @RequestParam("diagnosticosClinicos") String diagnosticosClinicos,
                                 @PathVariable("id") Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Consulta consulta = new Consulta();
        consulta.setResultadosEstudios(new ArrayList<>());
        LocalDate fechahoy = LocalDate.now();
        LocalTime tiempohoy = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
        consulta.setHoraAtencion(tiempohoy);
        consulta.setFechaAtencion(fechahoy);
        consulta.setTipoAtencion(tipoAtencion);
        consulta.setPaciente(paciente);
        consulta.setDiagnostico(diagnostico);
        consulta.setDiagnosticosClinicos(diagnosticosClinicos);
        consultaService.guardarConsulta(consulta);

        if (tiposInformes != null && informesEstudios != null) {
            for (int i = 0; i < tiposInformes.size(); i++) {
                ResultadoEstudio resultado = new ResultadoEstudio();
                resultado.setTipoInforme(tiposInformes.get(i));
                resultado.setInformeEstudio(informesEstudios.get(i));
                resultado.setFecha(fechahoy);
                resultado.setHora(tiempohoy);
                resultado.setConsulta(consulta);
                consulta.agregarResultadoEstudio(resultado);
                resultadoEstudioService.guardarResultadoEstudio(resultado);
            }
        }
        paciente.agregarConsultas(consulta);
        consultaService.guardarConsulta(consulta);
        pacienteService.crearPaciente(paciente);

        return "redirect:/pacientes/consultas/"+paciente.getId();
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

}
