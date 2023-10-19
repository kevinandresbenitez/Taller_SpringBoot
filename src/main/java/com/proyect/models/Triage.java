package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@ToString
@Table(name="triage")
public class Triage implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    private String color;
    private LocalDate fechaEvaluacion;
    private LocalTime horaEvaluacion;  

}
