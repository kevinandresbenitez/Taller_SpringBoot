/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ulise
 */
@Entity
@Table(name="consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="fecha_atencion")
    private Date fechaAtencion;
    @Column(name="hora_atencion")
    private Date horaAtencion;
    @Column(name="diagnostico")
    private String diagnostico;
    @Column(name="tipo_atencion")
    private String tipoAtencion;
    @Column(name="diagnosticos_clinicos")
    private String diagnosticosClinicos;
    @OneToMany(mappedBy = "consulta")
    private List<ResultadoEstudio> resultadosEstudios;
    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;


}
