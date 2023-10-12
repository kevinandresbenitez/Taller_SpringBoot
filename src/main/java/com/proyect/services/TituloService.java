package com.proyect.services;

import com.proyect.models.Titulo;
import com.proyect.repositories.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TituloService{

    @Autowired
    private TituloRepository tituloRepository;


    public void crearTitulo(Titulo titulo){
        this.tituloRepository.save(titulo);
    }

    public void eliminarTituloPorId(Long id){
        this.tituloRepository.deleteById(id);
    }

    
}
