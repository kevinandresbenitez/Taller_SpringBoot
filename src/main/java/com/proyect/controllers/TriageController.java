package com.proyect.controllers;

import com.proyect.models.Triage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TriageController {
    private int puntuacionTotal;
    private String colorNivel = " ";
    private String tiempoEspera = " ";
    private int nivel;
    private Map<String, Integer> niveles = new HashMap<>();
    private Map<String, String> tiemposDeEspera = new HashMap<>();

    public TriageController() {

        niveles.put("Rojo", 1);
        niveles.put("Naranja", 2);
        niveles.put("Amarillo", 3);
        niveles.put("Verde", 4);
        niveles.put("Azul", 5);


        tiemposDeEspera.put("Rojo", "Inmediata");
        tiemposDeEspera.put("Naranja", "15 minutos");
        tiemposDeEspera.put("Amarillo", "60 minutos");
        tiemposDeEspera.put("Verde", "2 horas");
        tiemposDeEspera.put("Azul", "4 horas");

    }

    @GetMapping("/ingreso-valores-sintomas")
    public String mostrarFormulario() {
        return "ingreso-valores-sintomas";
    }
    @PostMapping("/form-triage")
    public String calcularPuntuacionTotal(
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

        Triage triage = new Triage();

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

        puntuacionTotal = calcularPuntuacionTotal(triage);


        model.addAttribute("puntuacionTotal", puntuacionTotal);

        return "redirect:/resultado-triage";
    }

    private int calcularPuntuacionTotal(Triage triage) {

        int puntuacionTotal = triage.getRespiracion() + triage.getPulso() + triage.getEstadoMental() +
                triage.getConciencia() + triage.getDolorPechoRespirar() + triage.getLesionesGraves() + triage.getFiebre() + triage.getVomitos() + triage.getDolorAbdominal() +
                triage.getSignosShock() + triage.getLesionesLeves() + triage.getSangrado();

        return puntuacionTotal;
    }

    @GetMapping("/resultado-triage")
    public String resultadoTriage(Model model) {

        if (puntuacionTotal >= 15) {
            colorNivel = "Rojo";
        } else if (puntuacionTotal >= 10 ) {
            colorNivel = "Naranja";
        } else if (puntuacionTotal >= 9) {
            colorNivel = "Amarillo";
        } else if (puntuacionTotal >= 5) {
            colorNivel = "Verde";
        } else {
            colorNivel = "Azul";
        }

        nivel = niveles.get(colorNivel);
        tiempoEspera = tiemposDeEspera.get(colorNivel);

        model.addAttribute("nivel", nivel);
        model.addAttribute("colorNivel", colorNivel);
        model.addAttribute("tiempoEspera", tiempoEspera);

        return "resultado-triage";
    }

    @PostMapping("/cambiar-color")
    public String cambiarColor(String nuevoColor, Model model) {

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
    }

}
