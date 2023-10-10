package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.repositories.ConsultaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private ConsultaRepository consultaRepository;

    public void guardarConsulta(Consulta consulta){
        consultaRepository.save(consulta);
    }
}
