package com.proyect.services;

import com.proyect.models.ContactoPaciente;
import com.proyect.repositories.ContactoPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoPacienteService {


    @Autowired
    ContactoPacienteRepository contactoPacienteRepository;

    public void guardarContacto(ContactoPaciente contacto){
        contactoPacienteRepository.save(contacto);
    }
}
