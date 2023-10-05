/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;

import lombok.*;
import jakarta.persistence.*;
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

public class ResultadoEstudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "hora")
    private String hora;
    @Column(name = "tipoInforme")
    private String tipoInforme;
    @Column(name = "informeEstudio")
    private String informeEstudio;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}