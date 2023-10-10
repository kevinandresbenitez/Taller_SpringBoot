package com.proyect.services;

import com.proyect.models.Medico;
import com.proyect.repositories.MedicoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private MedicoRepository medicoRepository;

    public List<Medico> listarEnfermeros(){
        return this.medicoRepository.findAll();
    }
}
