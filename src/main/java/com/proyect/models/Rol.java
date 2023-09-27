/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;
import com.proyect.enums.TipoRol;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author kevin
 */
@Entity
@Setter
@Getter
@Table(name="rol")
@ToString
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "rol")
    private TipoRol rol;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;


}
