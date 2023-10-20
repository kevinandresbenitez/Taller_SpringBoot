/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.utils;
import java.util.*;

import java.util.HashMap;
import lombok.*;

/**
 *
 * @author kevin
 */
@Setter
@Getter
public class TriageCalculador {
    private List<TriageObject> listaDeTriages = new ArrayList<>();
    private int respiracion;
    private int pulso;
    private int estadoMental;
    private int conciencia;
    private int dolorPechoRespirar;
    private int lesionesGraves;
    private int fiebre;
    private int vomitos;
    private int dolorAbdominal;
    private int signosShock;
    private int lesionesLeves;
    private int sangrado;
    private Long edad;

    public TriageCalculador() {
        this.setTriageObject();
    }

    public void setTriageObject(){
        TriageObject Rojo = new TriageObject("Rojo","inmediata",1);
        TriageObject Naranja = new TriageObject("Naranja","15 minutos",2);
        TriageObject Amarillo = new TriageObject("Amarillo","60 minutos",3);
        TriageObject Verde = new TriageObject("Verde","2 horas",4);
        TriageObject Azul = new TriageObject("Azul","4 horas",5);
        this.listaDeTriages.add(Rojo);
        this.listaDeTriages.add(Naranja);
        this.listaDeTriages.add(Verde);
        this.listaDeTriages.add(Amarillo);
        this.listaDeTriages.add(Azul);
    }
    public Long obtenerPuntuacion(){
        return getConciencia() + getFiebre() +getPulso() + getDolorAbdominal()+ getEdad()+
                getEstadoMental()+getDolorPechoRespirar()+getLesionesGraves()+getLesionesLeves()+
                getSangrado()+getVomitos()+getRespiracion()+getSignosShock();

    }
    
    public TriageObject segunPuntuacionObtenerTriageObject(Long puntuacion){
        //Condiciones
        if (puntuacion >= 15) {
            return listaDeTriages.get(0);
        } else if (puntuacion >= 10 ) {
            return listaDeTriages.get(1);
        } else if (puntuacion >= 9) {
            return listaDeTriages.get(2);
        } else if (puntuacion >= 5) {
            return listaDeTriages.get(3);
        } else {
            return listaDeTriages.get(4);
        }
    }

    public TriageObject segunColorObtenerTriageObject(String color){
        if(Objects.equals(color, "Rojo")){
            return listaDeTriages.get(0);
        }else if(Objects.equals(color, "Naranja")){
            return listaDeTriages.get(1);
        }else if(Objects.equals(color, "Verde")){
            return listaDeTriages.get(2);
        }else if(Objects.equals(color, "Amarillo")){
            return listaDeTriages.get(3);
        }else{
            return listaDeTriages.get(4);
        }
    }
    
    // Geters y setters 


    public void setRespiracion(int respiracion) {
        this.respiracion = respiracion;
    }

    public void setPulso(int pulso) {
        this.pulso = pulso;
    }

    public void setEstadoMental(int estadoMental) {
        this.estadoMental = estadoMental;
    }

    public void setConciencia(int conciencia) {
        this.conciencia = conciencia;
    }

    public void setDolorPechoRespirar(int dolorPechoRespirar) {
        this.dolorPechoRespirar = dolorPechoRespirar;
    }

    public void setLesionesGraves(int lesionesGraves) {
        this.lesionesGraves = lesionesGraves;
    }

    public void setFiebre(int fiebre) {
        this.fiebre = fiebre;
    }

    public void setVomitos(int vomitos) {
        this.vomitos = vomitos;
    }

    public void setDolorAbdominal(int dolorAbdominal) {
        this.dolorAbdominal = dolorAbdominal;
    }

    public void setSignosShock(int signosShock) {
        this.signosShock = signosShock;
    }

    public void setLesionesLeves(int lesionesLeves) {
        this.lesionesLeves = lesionesLeves;
    }

    public void setSangrado(int sangrado) {
        this.sangrado = sangrado;
    }

    public void setEdad(Long edad) {
        if (edad > 1 && edad < 18 || edad > 60) {
            this.edad = 1L;
        } else {
            this.edad = 0L;
        }
    }
}
