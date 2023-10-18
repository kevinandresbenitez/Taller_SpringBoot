package com.proyect.services;

import com.proyect.models.Triage;
import com.proyect.repositories.TriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriageService {
    @Autowired
    TriageRepository triageRepository;

    public void guardarTriage(Triage triage){
        triageRepository.save(triage);
    }

    public Triage findTriageById(Long id){
        return triageRepository.findById(id).get();
    }
}
