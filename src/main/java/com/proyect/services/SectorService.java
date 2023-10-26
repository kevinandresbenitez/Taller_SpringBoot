package com.proyect.services;

import com.proyect.models.Sector;
import com.proyect.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {
    @Autowired
    SectorRepository sectorRepository;

    public List<Sector> listarSectores(){
        return sectorRepository.findAll();
    }


    public void crear(Sector sector) {
        this.sectorRepository.save(sector);
    }

    public Optional<Sector> obtenerPorId(Long id_sector) {
        return this.sectorRepository.findById(id_sector);
    }

    public void eliminarPorId(Long id) {
        this.sectorRepository.deleteById(id);
    }

    public List<Sector> obtenerSectoresDelFuncionario(Long id) {
         return sectorRepository.findByFuncionariosId(id);
    }

}
