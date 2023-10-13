/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.config;

import com.proyect.services.BoxService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduardo
 */
@Component
public class BoxCreation {
    
    @Autowired
    private BoxService boxService;

    @PostConstruct
    public void initializeBoxes() {
        // Crea los 10 Box al inicio con habilitado = true
        for (int i = 1; i <= 10; i++) {
            boxService.crearBox(i, true);
        }
    }

   
}
    

