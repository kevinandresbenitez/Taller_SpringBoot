package com.proyect.services;

import com.proyect.models.Sector;
import com.proyect.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {
    @Autowired
    SectorRepository sectorRepository;

    public List<Sector> listaSectores(){
        return sectorRepository.findAll();
    }

}
