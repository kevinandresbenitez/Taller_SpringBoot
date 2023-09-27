package com.proyect.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sector")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="sectorTrabajo")
    private String sectorTrabajo;
    @ManyToOne
    @JoinColumn(name="funcionario")
    private Funcionario funcionario;
}