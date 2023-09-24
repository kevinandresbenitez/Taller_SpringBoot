/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;

/**
 *
 * @author kevin
 */


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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(int telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public int getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(int telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    
    
    
}
