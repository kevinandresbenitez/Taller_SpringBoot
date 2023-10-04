package com.proyect.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="profesional_salud")
public class ProfesionalSalud{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nro_matricula")
    private Long nroMatricula;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
}
