package com.proyect.services;

import com.proyect.models.Triage;
import com.proyect.repositories.TriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * el servicio TriageService maneja la logica asociada con los triages
 * interactua con el repositorio TriageRepository para realizar operaciones de persistencia y recuperacion de datos
 */
@Service
public class TriageService {

    @Autowired
    private TriageRepository triageRepository;

    /**
     * guarda un objeto Triage en el repositorio
     *
     * @param triage objeto Triage que se va a almacenar
     */
    public void guardarTriage(Triage triage) {
        triageRepository.save(triage);
    }

    /**
     * busca triages en un rango especifico de fechas
     *
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin    fecha de fin del rango
     * @return lista de triages que tienen fecha de evaluacion dentro del rango especificado
     */
    public List<Triage> findTriageEnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return triageRepository.findTriageByFechaEvaluacionBetween(fechaInicio, fechaFin);
    }

    /**
     * Busca un triage por su identificador unico
     *
     * @param id identificador único del triage
     * @return objeto Triage correspondiente al id proporcionado
     */
    public Triage findTriageById(Long id) {
        return triageRepository.findById(id).orElse(null);
    }
}
