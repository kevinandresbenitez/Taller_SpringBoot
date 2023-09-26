/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author kevin
 */
@Entity
@Setter
@EqualsAndHashCode
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
    @ManyToMany(mappedBy = "funcionario")
    private List<Roles> roles;


    
}
