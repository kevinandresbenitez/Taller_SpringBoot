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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="paciente")
public class Paciente extends Persona implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "paciente")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<contactoPaciente> contactos;
    @OneToMany(mappedBy = "paciente")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Consulta> consultas;
    @OneToMany(mappedBy = "paciente")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Triage> triages;
    @OneToMany(mappedBy = "paciente")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<ResultadoEstudio> resultadosEstudios;
    @OneToMany(mappedBy = "paciente")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Ingreso> ingresos;



}
