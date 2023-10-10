package com.proyect.services;

import com.proyect.models.Enfermero;
import com.proyect.repositories.EnfermeroRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EnfermeroService {

    private EnfermeroRepository enfermeroRepository;

    public List<Enfermero> listarEnfermeros(){
        return this.enfermeroRepository.findAll();
    }
}
