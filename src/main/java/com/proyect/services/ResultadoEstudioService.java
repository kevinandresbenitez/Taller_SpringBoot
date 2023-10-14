package com.proyect.services;

import com.proyect.models.ResultadoEstudio;
import com.proyect.repositories.ResultadoEstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoEstudioService {
    @Autowired
    private ResultadoEstudioRepository resultadoEstudioRepository;

    public List<ResultadoEstudio> listarResultados() {
        return resultadoEstudioRepository.findAll();
    }

    public void guardarResultadoEstudio(ResultadoEstudio resultadoEstudio) {
        resultadoEstudioRepository.save(resultadoEstudio);
    }


    public void eliminarEstudio(Long id) {
        resultadoEstudioRepository.deleteById(id);
    }

    public void modificarEstudio(ResultadoEstudio resultadoEstudio) {
        resultadoEstudioRepository.save(resultadoEstudio);
    }

    public ResultadoEstudio obtenerEstudioPorId(Long id) {
        return resultadoEstudioRepository.findById(id).orElse(null);
    }

}

