/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;

import com.proyect.models.Box;
import com.proyect.repositories.BoxRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
        return boxRepository.save(box);
    }

    public List<Box> listarBoxes() {
        return boxRepository.findAll();
    }
    
    public Optional<Box> obtenerBoxPorId(Long id) {
        return boxRepository.findById(id);
    }

    public void habilitarBox(Long id) {
        if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
            boxRepository.save(box);
        } else {

        }
    }

    public void deshabilitarBox(Long id) {
        if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
            boxRepository.save(box);
        } else {

        }

    }

    public Box guardarBox(Box box) {
        return this.boxRepository.save(box);
    }
}
