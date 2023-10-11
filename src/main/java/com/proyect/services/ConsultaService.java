package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void guardarConsulta(Consulta consulta){
        consultaRepository.save(consulta);
    }

    public Consulta obtenerConsultaPorId(Long id){
        return consultaRepository.findById(id).get();
    }
}
