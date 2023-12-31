package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name="titulo")
public class Titulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "universidad")
    private String universidad;
    @Column(name = "fecha_graduacion")
    private LocalDate fechaGraduacion;
    
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;
    
}
