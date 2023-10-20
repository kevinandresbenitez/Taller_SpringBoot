package com.proyect.services;

import com.proyect.models.TriageModificacion;
import com.proyect.repositories.TriageModificacionRepository;
import com.proyect.repositories.TriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriageModificacionService {
    @Autowired
    private TriageModificacionRepository triageModificacionRepository;

    public void guardarModificacion(TriageModificacion modificacion){
        triageModificacionRepository.save(modificacion);
    }
}
