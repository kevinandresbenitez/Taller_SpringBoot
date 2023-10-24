package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.ConsultaRepository;
import com.proyect.repositories.MedicoRepository;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaService consultaService;

    public List<Medico> listarMedicos(){
        return medicoRepository.findAll();
    }
    public Boolean esProfSaludMedico(Long idProfSalud) {
        return this.medicoRepository.existsByProfesionalSaludId(idProfSalud);
    }
    
    public void asignarProfSaludComoMedico(ProfesionalSalud profesionalSalud) {
        Medico medico = new Medico();
        medico.setProfesionalSalud(profesionalSalud);
        this.medicoRepository.save(medico);
    }
    
    public void eliminarMedicoPorId(Long id){
        this.medicoRepository.deleteById(id);
    }

    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id);
    }


    public Medico medicoQueMasAtendio(LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todos los médicos
        List<Medico> medicos = listarMedicos();

        // Inicializar un mapa para realizar un seguimiento de la cantidad de pacientes atendidos por cada médico
        Map<Long, Integer> pacientesAtendidosPorMedico = new HashMap<>();

        // Iterar a través de los médicos y contar la cantidad de pacientes atendidos por cada uno
        for (Medico medico : medicos) {
            int pacientesAtendidos = consultaService.cantidadPacientesAtendidosPorMedico(medico, fechaInicio, fechaFin);
            pacientesAtendidosPorMedico.put(medico.getId(), pacientesAtendidos);
        }

        // Encontrar al médico que atendió a más pacientes
        Long medicoIdMasAtendido = Collections.max(pacientesAtendidosPorMedico.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Obtener el médico a partir de su ID
        return obtenerMedicoPorId(medicoIdMasAtendido).orElse(null);
    }

    
    public Optional<Medico> obtenerMedicoPorIdProfSalud(Long idProfSalud){
        return this.medicoRepository.findByProfesionalSaludId(idProfSalud);
    }
}
