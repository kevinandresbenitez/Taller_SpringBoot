/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author alvez
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="resultado_estudio")

public class ResultadoEstudio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "hora")
    private LocalTime hora;
    @Column(name = "tipo_informe")
    private String tipoInforme;
    @Column(name = "informe_estudio")
    private String informeEstudio;
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
}