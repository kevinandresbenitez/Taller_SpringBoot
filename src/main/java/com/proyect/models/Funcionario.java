/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;
import jakarta.persistence.*;
import java.util.List;

import java.io.Serializable;
import lombok.*;

/**
 *
 * @author kevin
 */
@Entity
@Setter
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="funcionario")
public class Funcionario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "contraseña")
    private String contraseña;
    @OneToMany(mappedBy = "funcionario")
    private List<Rol> roles;
}
