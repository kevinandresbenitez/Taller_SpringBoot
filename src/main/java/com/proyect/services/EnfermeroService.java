package com.proyect.services;

import com.proyect.models.Enfermero;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.EnfermeroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnfermeroService {

    @Autowired
    private EnfermeroRepository enfermeroRepository;

    public List<Enfermero> listarEnfermeros(){
        return this.enfermeroRepository.findAll();
    }

    public Boolean esProfSaludEnfermero(Long idProfSalud) {
        return this.enfermeroRepository.existsByProfesionalSaludId(idProfSalud);
    }

    public void asignarProfSaludComoEnfermero(ProfesionalSalud profesionalSalud) {
        Enfermero enfermero = new Enfermero();
        enfermero.setProfesionalSalud(profesionalSalud);
        this.enfermeroRepository.save(enfermero);
    }
    
    public void eliminarEnfermeroPorId(Long id){
        this.enfermeroRepository.deleteById(id);
    }
}
