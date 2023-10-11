package com.proyect.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name="especialidad")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_titulacion")
    private String fechaTitulacion;
    @Column(name = "universidad")
    private String universidad;
    
}
