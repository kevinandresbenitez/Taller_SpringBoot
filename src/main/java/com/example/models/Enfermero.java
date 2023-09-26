/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
/**
 *
 * @author kevin
 */


@Setter
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity
public class Enfermero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
}
