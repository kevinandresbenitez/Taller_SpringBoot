package com.proyect.services;

import com.proyect.models.ContactoPaciente;
import com.proyect.repositories.ContactoPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para gestionar contactos de pacientes.
 */
@Service
public class ContactoPacienteService {


    @Autowired
    ContactoPacienteRepository contactoPacienteRepository;
    
    /**
     * Metodo para guardar un contacto de paciente en el sistema.
     *
     * @param contacto  contacto de paciente que se va a guardar.
     */
    public void guardarContacto(ContactoPaciente contacto){
        contactoPacienteRepository.save(contacto);
    }
}
