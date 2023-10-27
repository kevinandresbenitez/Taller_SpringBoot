package com.proyect.services;

import com.proyect.models.Sector;
import com.proyect.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * el servicio SectorService maneja la logica asociada con los sectores
 * interactua con el repositorio SectorRepository para realizar operaciones de persistencia y recuperacion de datos
 */
@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    /**
     * obtiene una lista de todos los sectores disponibles
     *
     * @return lista de objetos Sector
     */
    public List<Sector> listarSectores() {
        return sectorRepository.findAll();
    }

    /**
     * crea un nuevo sector y lo guarda en el repositorio
     *
     * @param sector objeto Sector que se va a crear y almacenar
     */
    public void crear(Sector sector) {
        sectorRepository.save(sector);
    }

    /**
     * obtiene un sector por su identificador único
     *
     * @param id_sector identificador unico del sector
     * @return objeto Optional que puede contener un objeto Sector si se encuentra
     */
    public Optional<Sector> obtenerPorId(Long id_sector) {
        return sectorRepository.findById(id_sector);
    }

    /**
     * elimina un sector por su identificador unico
     *
     * @param id identificador unico del sector que se va a eliminar
     */
    public void eliminarPorId(Long id) {
        sectorRepository.deleteById(id);
    }

    /**
     * obtiene una lista de sectores asociados a un funcionario por su identificador unico
     *
     * @param id identificador unico del funcionario
     * @return lista de objetos Sector asociados al funcionario
     */
    public List<Sector> obtenerSectoresDelFuncionario(Long id) {
        return sectorRepository.findByFuncionariosId(id);
    }
}
