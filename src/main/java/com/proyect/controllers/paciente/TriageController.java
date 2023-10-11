package com.proyect.controllers.paciente;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proyect.utils.TriageCalculador;
import com.proyect.utils.TriageObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pacientes/triages")
public class TriageController {

    @GetMapping("/{id}") // Id del paciente para mostrar la lista de triages
    public String listarTriages(@PathVariable("id") Long id) {
        // Buscamos los triages de este paciente y se los mostramos
        // Faltan hacer los repositoris 
        return "pacientes/triages/index";
    }
    
    @GetMapping("/agregar/{id}") // Id del paciente a asignar triage
    public String mostrarFormulario(@PathVariable("id") Long id){
        return "pacientes/triages/crear";
    }
    
    @PostMapping("/agregar/{id}") // Procesamos el triage y lo guardamos
    public String calcularPuntuacionTotal(
            @PathVariable("id") Long id,
            @RequestParam("respiracion") int respiracion,
            @RequestParam("pulso") int pulso,
            @RequestParam("estadoMental") int estadoMental,
            @RequestParam("conciencia") int conciencia,
            @RequestParam("dolorPechoRespirar") int dolorPechoRespirar,
            @RequestParam("lesionesGraves") int lesionesGraves,
            @RequestParam("fiebre") int fiebre,
            @RequestParam("vomitos") int vomitos,
            @RequestParam("dolorAbdominal") int dolorAbdominal,
            @RequestParam("signosShock") int signosShock,
            @RequestParam("lesionesLeves") int lesionesLeves,
            @RequestParam("sangrado") int sangrado,
            Model model) {

        TriageCalculador triage = new TriageCalculador();
        triage.setRespiracion(respiracion);
        triage.setPulso(pulso);
        triage.setEstadoMental(estadoMental);
        triage.setConciencia(conciencia);
        triage.setDolorPechoRespirar(dolorPechoRespirar);
        triage.setLesionesGraves(lesionesGraves);
        triage.setFiebre(fiebre);
        triage.setVomitos(vomitos);
        triage.setDolorAbdominal(dolorAbdominal);
        triage.setSignosShock(signosShock);
        triage.setLesionesLeves(lesionesLeves);
        triage.setSangrado(sangrado);
        
        // Obteniendo punuacion y respectivo color, tiempo de espera ...
        int puntuacion = triage.obtenerPuntuacion();
        TriageObject TriageResultante = triage.segunPuntuacionObtenerTriageObject(puntuacion);
        // Aca tenemos que hacer un new Triage() y luego guardarlo en un service
        // Este traiage al guardarse, podra verse desde la pagina principal de traige del paciente

        return "redirect:/" + id;
    }


    @GetMapping("/resultado-triage")
    public String resultadoTriage(Model model) {
        /*
        nivel = niveles.get(colorNivel);
        tiempoEspera = tiemposDeEspera.get(colorNivel);

        model.addAttribute("nivel", nivel);
        model.addAttribute("colorNivel", colorNivel);
        model.addAttribute("tiempoEspera", tiempoEspera);
        */
        return "resultado-triage";
    }

    @PostMapping("/cambiar-color")
    public String cambiarColor(String nuevoColor, Model model) {
        /*
        if (niveles.containsKey(nuevoColor)) {

            this.colorNivel = nuevoColor;
            nivel = niveles.get(nuevoColor);
            tiempoEspera = tiemposDeEspera.get(nuevoColor);

            return "redirect:/cambio-color";
        } else {

            String mensaje = " El color no es válido.";
            model.addAttribute("mensaje", mensaje);
            return "pagina-de-error";
        }
        */
        return "resultado-triage";
    }

}
