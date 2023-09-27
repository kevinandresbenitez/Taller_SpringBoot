/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.models;
import jakarta.persistence.*;
import lombok.*;
/**
 *
 * @author kevin
 */

@Setter
@Getter
@ToString
@MappedSuperclass
public abstract class Persona{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Column(name = "email")
    private String email;
    @Column(name = "dni")
    private int dni;
    @Column(name = "telefono_fijo")
    private int telefonoFijo;
    @Column(name = "telefono_celular")
    private int telefonoCelular;
}
