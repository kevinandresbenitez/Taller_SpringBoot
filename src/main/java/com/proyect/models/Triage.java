package com.proyect.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Triage {

    private Paciente paciente;
    //private Medico medico;
    private LocalDate fechaEvaluacion;
    private LocalTime horaEvaluacion;
    private int respiracion;
    private int pulso;
    private int estadoMental;
    private int conciencia;
    private int dolorPechoRespirar;
    private int lesionesGraves;
    private int fiebre;
    private int vomitos;
    private int dolorAbdominal;
    private int signosShock;
    private int lesionesLeves;
    private int sangrado;

    public Triage() {
    }

    public Triage(Paciente paciente, /*Medico medico*/ LocalDate fechaEvaluacion, LocalTime horaEvaluacion, int respiracion,
                  int pulso, int estadoMental, int conciencia, int dolorPechoRespirar, int lesionesGraves, int edad,
                  int fiebre, int vomitos, int dolorAbdominal, int signosShock, int lesionesLeves, int sangrado) {

        this.paciente = paciente;
        this.medico = medico;
        this.fechaEvaluacion = fechaEvaluacion;
        this.horaEvaluacion = horaEvaluacion;
        this.respiracion = respiracion;
        this.pulso = pulso;
        this.estadoMental = estadoMental;
        this.conciencia = conciencia;
        this.dolorPechoRespirar = dolorPechoRespirar;
        this.lesionesGraves = lesionesGraves;
        this.fiebre = fiebre;
        this.vomitos = vomitos;
        this.dolorAbdominal = dolorAbdominal;
        this.signosShock = signosShock;
        this.lesionesLeves = lesionesLeves;
        this.sangrado = sangrado;
    }



}
