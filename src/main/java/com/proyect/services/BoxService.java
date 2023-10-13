/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;

import com.proyect.models.Box;
import com.proyect.repositories.BoxRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author eduardo
 */
@Service
@Transactional
public class BoxService {
    @Autowired
    
    BoxRepository boxRepository;
    
     public Box crearBox(int numero, boolean ocupado) {
        Box box = new Box();
        box.setNumero(numero);
        box.setOcupado(ocupado);
        return boxRepository.save(box);
    }
      public void habilitarBox(Long id) {
         if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
            box.setHabilitado(true);
            boxRepository.save(box);
         }else{
          
      }
    }

    public void deshabilitarBox(Long id) {
            if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
            box.setHabilitado(false);
            boxRepository.save(box);
         }else{
          
      }
    
        }
}

