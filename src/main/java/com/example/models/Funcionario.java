/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import java.util.List;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

/**
 *
 * @author kevin
 */
@Entity
@Setter
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Funcionario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "contraseña")
    private String contraseña;
    @OneToMany(mappedBy = "funcionario")
    private List<Roles> roles;
}
