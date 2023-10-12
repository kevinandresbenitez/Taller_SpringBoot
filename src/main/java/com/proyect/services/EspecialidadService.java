package com.proyect.services;

import com.proyect.models.Especialidad;
import com.proyect.repositories.EspecialidadnRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadnRepository especialidadRepository;

    public List<Especialidad> listarEspecialidades(){
        return this.especialidadRepository.findAll();
    }

    public void crearEspecialidad(Especialidad especialidad){
        this.especialidadRepository.save(especialidad);
    }

    public void eliminarEspecialidadPorId(Long id){
        this.especialidadRepository.deleteById(id);
    }

    public Optional<Especialidad> obtenerEspecialidadPorId(Long id) {
        return this.especialidadRepository.findById(id);
    }
    
}
