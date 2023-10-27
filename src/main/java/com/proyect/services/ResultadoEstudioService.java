package com.proyect.services;

import com.proyect.models.ResultadoEstudio;
import com.proyect.repositories.ResultadoEstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * el servicio ResultadoEstudioService maneja la logica asociada con los resultados de estudios
 * interactúa con el repositorio ResultadoEstudioRepository para realizar operaciones de persistencia y recuperacion de datos
 */
@Service
public class ResultadoEstudioService {

    @Autowired
    private ResultadoEstudioRepository resultadoEstudioRepository;

    /**
     * obtiene una lista de todos los resultados de estudios disponibles
     *
     * @return Lista de objetos ResultadoEstudio
     */
    public List<ResultadoEstudio> listarResultados() {
        return resultadoEstudioRepository.findAll();
    }

    /**
     * guarda un nuevo resultado de estudio en el repositorio
     *
     * @param resultadoEstudio objeto ResultadoEstudio que se va a guardar
     */
    public void guardarResultadoEstudio(ResultadoEstudio resultadoEstudio) {
        resultadoEstudioRepository.save(resultadoEstudio);
    }

    /**
     * elimina un resultado de estudio por su identificador unico
     *
     * @param id identificador único del resultado de estudio que se va a eliminar
     */
    public void eliminarEstudio(Long id) {
        resultadoEstudioRepository.deleteById(id);
    }

    /**
     * modifica un resultado de estudio en el repositorio
     *
     * @param resultadoEstudio objeto ResultadoEstudio que se va a modificar
     */
    public void modificarEstudio(ResultadoEstudio resultadoEstudio) {
        resultadoEstudioRepository.save(resultadoEstudio);
    }

    /**
     * obtiene un resultado de estudio por su identificador unico
     *
     * @param id identificador único del resultado de estudio
     * @return objeto ResultadoEstudio si se encuentra, o `null` si no se encuentra
     */
    public ResultadoEstudio obtenerEstudioPorId(Long id) {
        return resultadoEstudioRepository.findById(id).orElse(null);
    }
}

