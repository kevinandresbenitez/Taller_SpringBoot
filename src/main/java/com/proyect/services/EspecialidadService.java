package com.proyect.services;

import com.proyect.models.Especialidad;
import com.proyect.repositories.EspecialidadnRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar las especialidades medicas.
 */
@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadnRepository especialidadRepository;
    
     /**
     * Metodo para obtener una lista de todas las especialidades disponibles.
     *
     * @return Una lista de especialidades.
     */
    public List<Especialidad> listarEspecialidades(){
        return this.especialidadRepository.findAll();
    }
    
/**
     * Metodo para crear una nueva especialidad y la guarda en el sistema.
     *
     * @param especialidad La especialidad a crear y guardar.
     */
    public void crearEspecialidad(Especialidad especialidad){
        this.especialidadRepository.save(especialidad);
    }
    
      /**
     * Elimina una especialidad por su ID.
     *
     * @param id  Id de la especialidad a eliminar.
     */
    public void eliminarEspecialidadPorId(Long id){
        this.especialidadRepository.deleteById(id);
    }
    
    /**
     * Obtiene una especialidad por su ID.
     *
     * @param id  Id de la especialidad a buscar.
     * @return Un objeto Optional que puede contener la especialidad si se encuentra,
     * o estar vacío si no se encuentra.
     */
    public Optional<Especialidad> obtenerEspecialidadPorId(Long id) {
        return this.especialidadRepository.findById(id);
    }
    
}
