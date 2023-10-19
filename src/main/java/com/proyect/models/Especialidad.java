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
@Table(name="especialidad")
public class Especialidad implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    
    @OneToMany(mappedBy = "especialidad")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Titulo> titulos;
}
