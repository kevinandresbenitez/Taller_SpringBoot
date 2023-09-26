/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import lombok.*;
/**
 *
 * @author kevin
 */

@Setter
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@MappedSuperclass
public abstract class Persona{
    
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "fechaNacimiento")
    private String fechaNacimiento;
    @Column(name = "estadoCivil")
    private String estadoCivil;
    @Column(name = "email")
    private String email;
    @Column(name = "dni")
    private int dni;
    @Column(name = "telefonoFijo")
    private int telefonoFijo;
    @Column(name = "telefonoCelular")
    private int telefonoCelular;
}
