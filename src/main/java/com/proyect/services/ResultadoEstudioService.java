package com.proyect.services;

import com.proyect.models.ResultadoEstudio;
import com.proyect.repositories.ResultadoEstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoEstudioService {
    @Autowired
    ResultadoEstudioRepository resultadoEstudioRepository;

    public void guardarResultadoEstudio(ResultadoEstudio resultadoEstudio){
        resultadoEstudioRepository.save(resultadoEstudio);
    }

}
