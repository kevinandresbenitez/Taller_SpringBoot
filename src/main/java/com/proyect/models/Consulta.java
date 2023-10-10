/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import lombok.*;

/**
 *
 * @author ulise
 */
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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
    @OneToMany(mappedBy = "consulta")
    private List<ResultadoEstudio> resultadosEstudios;
    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;

    public void agregarResultadoEstudio(ResultadoEstudio resultadoEstudio){
        this.resultadosEstudios.add(resultadoEstudio);
    }
}
