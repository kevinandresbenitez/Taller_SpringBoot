package com.proyect.services;

import com.proyect.models.Titulo;
import com.proyect.repositories.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * el servicio TituloService maneja la logica asociada con los titulos
 * interactua con el repositorio TituloRepository para realizar operaciones de persistencia y recuperacion de datos
 */
@Service
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    /**
     * crea un nuevo titulo y lo guarda en el repositorio
     *
     * @param titulo objeto Titulo que se va a crear y almacenar
     */
    public void crearTitulo(Titulo titulo) {
        tituloRepository.save(titulo);
    }

    /**
     * elimina un titulo por su identificador unico
     *
     * @param id identificador unico del titulo que se va a eliminar
     */
    public void eliminarTituloPorId(Long id) {
        tituloRepository.deleteById(id);
    }
}
