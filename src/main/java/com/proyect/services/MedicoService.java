package com.proyect.services;

import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.MedicoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> listarMedicos(){
        return this.medicoRepository.findAll();
    }
    public Boolean esProfSaludMedico(Long idProfSalud) {
        return this.medicoRepository.existsByProfesionalSaludId(idProfSalud);
    }
    
    public void asignarProfSaludComoMedico(ProfesionalSalud profesionalSalud) {
        Medico medico = new Medico();
        medico.setProfesionalSalud(profesionalSalud);
        this.medicoRepository.save(medico);
    }
    
    public void eliminarMedicoPorId(Long id){
        this.medicoRepository.deleteById(id);
    }

    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return this.medicoRepository.findById(id);
    }
}
