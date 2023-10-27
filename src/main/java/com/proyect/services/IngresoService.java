package com.proyect.services;

import com.proyect.models.Especialidad;
import com.proyect.models.Ingreso;
import com.proyect.repositories.EspecialidadnRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyect.repositories.IngresoRepository;

/**
 * Servicio para gestionar los ingresos.
 */
@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

      /**
     * Metodo para guardar un nuevo ingreso en el sistema.
     *
     * @param ingreso  ingreso a guardar.
     */
    public void guardarIngreso(Ingreso ingreso){
        ingresoRepository.save(ingreso);
    }
    
}
