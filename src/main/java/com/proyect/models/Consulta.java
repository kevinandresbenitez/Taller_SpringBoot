/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author ulise
 */

@Entity
@Setter
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Table(name="consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="fecha_atencion")
    private LocalDate fechaAtencion;
    @Column(name="hora_atencion")
    private LocalTime horaAtencion;
    @Column(name="diagnostico")
    private String diagnostico;
    @Column(name="tipo_atencion")
    private String tipoAtencion;
    @Column(name="diagnosticos_clinicos")
    private String diagnosticosClinicos;

    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name="id_medico")
    private Medico medico;
    @ManyToMany
    @JoinTable(name = "consulta_resultados_estudios",
                joinColumns = @JoinColumn(name = "id_consulta"),
                inverseJoinColumns = @JoinColumn(name = "id_resultados_estudios"))
    @ToString.Exclude
    private List<ResultadoEstudio> resultadoEstudios;
    @OneToOne
    @JoinColumn(name="id_ingreso")
    private Ingreso ingreso;
    @OneToOne
    @JoinColumn(name="id_triage")
    private Triage triage;


 }
