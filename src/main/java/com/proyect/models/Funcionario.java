/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;
import jakarta.persistence.*;
import java.util.List;

import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author kevin
 */
@Entity
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="funcionario")
public class Funcionario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "contraseña")
    private String contraseña;
    @Cascade(CascadeType.DELETE_ORPHAN)
    @ManyToMany
    @JoinTable(name = "funcionario_rol",
               joinColumns = @JoinColumn(name = "id_funcionario"),
               inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> roles;
    @OneToMany(mappedBy = "funcionario")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Sector> sectores;
    @OneToMany(mappedBy = "funcionario")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<Administrador> adminitradores;
    @OneToMany(mappedBy = "funcionario")
    @Cascade(CascadeType.DELETE_ORPHAN)
    private List<ProfesionalSalud> profesionalesSalud;
}
