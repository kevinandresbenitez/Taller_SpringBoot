package com.proyect.services;

import com.proyect.models.TriageModificacion;
import com.proyect.repositories.TriageModificacionRepository;
import com.proyect.repositories.TriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * El servicio TriageModificacionService maneja la lógica asociada con las modificaciones de triage
 * interactua con el repositorio TriageModificacionRepository para realizar operaciones de persistencia y recuperacion de datos
 */
@Service
public class TriageModificacionService {

    @Autowired
    private TriageModificacionRepository triageModificacionRepository;

    /**
     * guarda un objeto TriageModificacion en el repositorio
     *
     * @param modificacion objeto TriageModificacion que se va a almacenar
     */
    public void guardarModificacion(TriageModificacion modificacion) {
        triageModificacionRepository.save(modificacion);
    }

    /**
     * recupera una lista de todas las modificaciones de triage almacenadas
     *
     * @return lista de todas las modificaciones de triage
     */
    public List<TriageModificacion> listarModificacionesDeTriage() {
        return triageModificacionRepository.findAll();
    }
}
