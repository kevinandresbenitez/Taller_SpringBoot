package com.proyect.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name="medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_prof_salud")
    private ProfesionalSalud profesionalSalud;
    @OneToMany(mappedBy = "medico")
    private List<Especialidad> especialidades;
}
