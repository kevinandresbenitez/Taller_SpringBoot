package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Setter
@Getter
@Table(name="medico")
public class Medico implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_prof_salud")
    private ProfesionalSalud profesionalSalud;
    
    @OneToMany(mappedBy = "medico")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Titulo> titulos;
    
    @OneToMany(mappedBy = "medico")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Consulta> consultas;
}
