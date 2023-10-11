package com.proyect.controllers.paciente;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import com.proyect.models.ResultadoEstudio;
import com.proyect.services.ConsultaService;
import com.proyect.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
@RequestMapping("/pacientes/consultas")
public class ConsultaControlller {
    @Autowired
    ConsultaService consultaService;
    @Autowired
    PacienteService pacienteService;
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
    public String crearConsultas(/*@RequestParam("resultadosEstudios") List<ResultadoEstudio> resultadoEstudios,*/
                                 @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate fecha,
                                 @RequestParam("hora") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime hora,
                                 @RequestParam("diagnostico") String diagnostico,
                                 @RequestParam("tipoAtencion") String tipoAtencion,
                                 @RequestParam("diagnosticosClinicos") String diagnosticosClinicos,
                                 @PathVariable("id") Long id) {
        Paciente paciente = pacienteService.obtenerPacienteById(id);
        Consulta consulta = new Consulta();
        consulta.setHoraAtencion(hora);
        consulta.setFechaAtencion(fecha);
        consulta.setTipoAtencion(tipoAtencion);
        consulta.setPaciente(paciente);
        consulta.setDiagnostico(diagnostico);
        consulta.setDiagnosticosClinicos(diagnosticosClinicos);
        //consulta.setResultadosEstudios(resultadoEstudios);
        paciente.agregarConsultas(consulta);
        consultaService.guardarConsulta(consulta);
        return "redirect:/pacientes/consultas/"+paciente.getId();
    }

    @GetMapping("/resultadosestudios/{id}")
    public String listaResultadosEstudios(Model model, @PathVariable("id") Long id) {
        Consulta consultas = consultaService.obtenerConsultaPorId(id);
        if (consultas == null) {
            return "redirect:/pacientes/consultas/";
        }
        model.addAttribute("consultas", consultas);
        return "/pacientes/consultas/resultadosestudios/index";
    }

    @GetMapping("/resultadosestudios/crear/{id}")
    public String crearResultadoEstudios(){
        return "/pacientes/consultas/resultadosestudios/crear";
    }

    @PostMapping("/resultadosestudios/crear/{id}")
    public String crearResultadoEstudios(@RequestParam("fecha")LocalDate fecha,
                                         @RequestParam("hora")LocalTime hora,
                                         @RequestParam("tipoInforme")String tipoInforme,
                                         @RequestParam("informeEstudio")String informeEstudio,
                                         @RequestParam("id")Long id){

        Consulta consulta = consultaService.obtenerConsultaPorId(id);
        ResultadoEstudio resultadoEstudio = new ResultadoEstudio();
        resultadoEstudio.setInformeEstudio(informeEstudio);
        resultadoEstudio.setHora(hora);
        resultadoEstudio.setFecha(fecha);
        resultadoEstudio.setTipoInforme(tipoInforme);
        consulta.agregarResultadoEstudio(resultadoEstudio);
        consultaService.guardarConsulta(consulta);
        return "/pacientes/consultas/resultadosestudios/crear/"+consulta.getId();
    }
}
