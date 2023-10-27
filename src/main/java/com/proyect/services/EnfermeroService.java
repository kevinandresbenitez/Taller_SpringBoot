package com.proyect.services;

import com.proyect.models.Enfermero;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.EnfermeroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para gestionar enfermeros.
 */
@Service
public class EnfermeroService {

    @Autowired
    private EnfermeroRepository enfermeroRepository;
    
     /**
     *Llista todos los enfermeros en el sistema.
     *
     * @return Lista de enfermeros.
     */
    public List<Enfermero> listarEnfermeros(){
        return this.enfermeroRepository.findAll();
    }
    
/**
     * Metodo para verificar si un profesional de la salud es un enfermero.
     *
     * @param idProfSalud  Id del profesional de la salud a verificar.
     * @return `true` si el profesional de la salud es un enfermero, de lo contrario, `false`.
     */
    public Boolean esProfSaludEnfermero(Long idProfSalud) {
        return this.enfermeroRepository.existsByProfesionalSaludId(idProfSalud);
    }
    
    /**
     * Metodo para asignar un profesional de la salud como enfermero.
     *
     * @param profesionalSalud  profesional de la salud a asignar como enfermero.
     */
    public void asignarProfSaludComoEnfermero(ProfesionalSalud profesionalSalud) {
        Enfermero enfermero = new Enfermero();
        enfermero.setProfesionalSalud(profesionalSalud);
        this.enfermeroRepository.save(enfermero);
    }
    
    /**
     * Metodo para elimina un enfermero por su Id.
     *
     * @param id  Id del enfermero a eliminar.
     */
    public void eliminarEnfermeroPorId(Long id){
        this.enfermeroRepository.deleteById(id);
    }
}
