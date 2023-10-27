/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;

import com.proyect.models.Box;
import com.proyect.models.Paciente;
import com.proyect.repositories.BoxRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Service para gestionar metodos de los  enfermeros
 */
@Service
@Transactional
public class BoxService {

    @Autowired
    BoxRepository boxRepository;
    
     /**
     * Crea un nuevo Box con el número y estado especifico.
     *
     * @param numero   Número del Box.
     * @param ocupado  Indica si el Box está ocupado.
     * @return El Box creado y guardado en la base de datos.
     */
    public Box crearBox(int numero, boolean ocupado) {
        Box box = new Box();
        box.setNumero(numero);
        return boxRepository.save(box);
    }
    
/**
     * Encuentra un Box por el Id del Paciente al que está asignado.
     *
     * @param id  Id del Paciente para buscar en qué Box está asignado.
     * @return  Box asignado al Paciente o null si no se encuentra.
     */
    public Box findByPacienteId(Long id){
        return boxRepository.findByPacienteId(id);
    }
    
/**
     *  Lista todos los Boxes disponibles.
     *
     * @return Lista de Boxes disponibles.
     */
    public List<Box> listarBoxes() {
        return boxRepository.findAll();
    }
    
    /**
     * Obtiene un Box por su ID.
     *
     * @param id  Id del Box que se desea recuperar.
     * @return El Box encontrado o un Optional vacío si no se encuentra.
     */
    public Optional<Box> obtenerBoxPorId(Long id) {
        return boxRepository.findById(id);
    }
    
/**
     * Elimina un Box por su ID.
     *
     * @param id Id del Box que se desea eliminar.
     */
    public void eliminarBoxById(Long id){
        Optional<Box> box = obtenerBoxPorId(id);
        if(box.isPresent()){
            boxRepository.delete(box.get());
        }
    }
    /*
    /**
     * Habilita un Box por su ID.
     *
     * @param id Id del Box que se desea habilitar.
     */
   /* public void habilitarBox(Long id) {
        if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
             box.setHabilitado(true);
            boxRepository.save(box);
        } else {

        }
    }
    
 /**
     * Deshabilita un Box por su ID.
     *
     * @param id Id del Box que se desea deshabilitar.
     */
   /* public void deshabilitarBox(Long id) {
        if (boxRepository.existsById(id)) {
            Box box = boxRepository.findById(id).get();
            boxRepository.save(box);
        } else {

        }

    }*/
    
    
    
     /**
     * Guarda un Box en la base de datos.
     *
     * @param box  Box que se va a guardar.
     * @return El Box guardado.
     */
    public Box guardarBox(Box box) {
        return this.boxRepository.save(box);
    }
}
