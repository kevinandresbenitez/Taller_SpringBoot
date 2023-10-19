package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name="ingreso")
public class Ingreso implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
    
    @Column(name = "motivo")
    private String motivo;
    
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Column(name = "hora_registro")
    private LocalTime horaRegistro;
    
}
