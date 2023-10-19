package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="profesional_salud")
public class ProfesionalSalud implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nro_matricula")
    private Long nroMatricula;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
    
    @OneToMany(mappedBy = "profesionalSalud")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Medico> medicos;
    
    @OneToMany(mappedBy = "profesionalSalud")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Enfermero> enfermeros;
}
