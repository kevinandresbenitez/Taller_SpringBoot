/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import lombok.*;

/**
 *
 * @author kevin
 */
@Setter
@Getter
public class TriageCalculador {
    List<TriageObject> listaDeTriages = new ArrayList<>();
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
    
    public void constructor(){
        // Agregando los elementos por defecto
        TriageObject Rojo = new TriageObject("Rojo","inmediata",1);
        TriageObject Naranja = new TriageObject("Naranja","15 minutos",2);
        TriageObject Amarillo = new TriageObject("Amarillo","60 minutos",3);
        TriageObject Verde = new TriageObject("Verde","2 horas",4);
        TriageObject Azul = new TriageObject("Azul","4 horas",5);
        listaDeTriages.add(Rojo);
        listaDeTriages.add(Naranja);
        listaDeTriages.add(Verde);
        listaDeTriages.add(Amarillo);
        listaDeTriages.add(Azul);
    }
    
    public int obtenerPuntuacion(){
        return getPuntuacionRespiracion() + getPuntuacionPulso() + getPuntuacionEstadoMental() + getPuntuacionVomitos()
               + getPuntuacionVomitos() + getPuntuacionDolorPechoRespirar() + getPuntuacionLesionesGraves()
               + getPuntuacionFiebre() + getPuntuacionDolorAbdominal() + getPuntuacionSignosShock() + getPuntuacionlesionesLeves()
               + getPuntuacionSangrado();
    }
    
    public TriageObject segunPuntuacionObtenerTriageObject(int puntuacion){
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
    
    // Geters y setters 
    
    public int getPuntuacionRespiracion(){
        return 1;
    }
        public int getPuntuacionPulso(){
        return 1;
    }
        public int getPuntuacionEstadoMental(){
        return 1;
    }
        public int getPuntuacionVomitos(){
        return 1;
    }
        
        public int getPuntuacionDolorPechoRespirar(){
        return 1;
    }
        public int getPuntuacionLesionesGraves(){
        return 1;
    }
        public int getPuntuacionFiebre(){
        return 1;
    }
        public int getPuntuacionDolorAbdominal(){
        return 1;
    }
        public int getPuntuacionSignosShock(){
        return 1;
    }
        public int getPuntuacionlesionesLeves(){
        return 1;
    }
        public int getPuntuacionSangrado(){
        return 1;
    }
    
    
    
}
