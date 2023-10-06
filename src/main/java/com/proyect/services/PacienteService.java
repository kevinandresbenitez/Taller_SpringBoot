package com.proyect.services;

import com.proyect.models.Paciente;

import com.proyect.models.ResultadoEstudio;
import com.proyect.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Optional<Paciente> findByDni(int dni){
        return pacienteRepository.findByDni(dni);
    }

    public void crearPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes(){
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente obtenerPacienteById(Long id){
        return pacienteRepository.findById(id).get();
    }

    public void crearResultadoEstudio(List<ResultadoEstudio> resultadoEstudio,Long id){
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        Paciente paciente1 = paciente.get();
        paciente1.setResultadoEstudios(resultadoEstudio);
    }
    //public Triage asignarTriage();
}
