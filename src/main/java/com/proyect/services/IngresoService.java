package com.proyect.services;

import com.proyect.models.Especialidad;
import com.proyect.models.Ingreso;
import com.proyect.repositories.EspecialidadnRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyect.repositories.IngresoRepository;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository registroRepository;

    public void guardarRegistro(Ingreso registro){
        registroRepository.save(registro);
    }
    
}
