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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TriageObject {
    String color;
    String tiempoEspera;
    int nivel;
    
    public void constructor(String color, String tiempoEspera,int nivel){
        color = color;
        tiempoEspera = tiempoEspera;
        nivel = nivel;
    }

    public int ordenarColor(String color) {
        switch (color) {
            case "Rojo":
                return 1;
            case "Naranja":
                return 2;
            case "Amarillo":
                return 3;
            case "Verde":
                return 4;
            case "Azul":
                return 5;
            default:
                return 6;
        }
    }
}
