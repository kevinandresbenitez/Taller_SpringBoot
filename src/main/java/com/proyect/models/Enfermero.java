package com.proyect.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name="enfermero")
public class Enfermero{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_prof_salud")
    private ProfesionalSalud profesionalSalud;
}
