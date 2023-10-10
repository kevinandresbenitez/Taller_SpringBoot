/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.utils;

import lombok.*;

/**
 *
 * @author kevin
 */

@Getter
@Setter
@AllArgsConstructor
public class TriageObject {
    String Color;
    String TiempoEspera;
    int Nivel;
    
    public void constructor(String color, String tiempoEspera,int nivel){
        Color = color;
        TiempoEspera = tiempoEspera;
        Nivel = nivel;
    }
}
