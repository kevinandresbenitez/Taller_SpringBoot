package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.MedicoRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

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

    public int cantidadPacientesAtendidosPorMedico(long idMedico, Date fechaInicio, Date fechaFin) {

        // Obtener las consultas realizadas por el médico en el rango de fechas
        List<Consulta> consultas = medicoRepository.findConsultasByIdAndConsultasFechaAtencionBetween(idMedico, fechaInicio, fechaFin);

        // Contar la cantidad de pacientes atendidos por el médico
        Set<Long> pacientesAtendidos = new HashSet<>();
        for (Consulta consulta : consultas) {
            pacientesAtendidos.add(consulta.getPaciente().getId());
        }

        return pacientesAtendidos.size();
    }

    public Medico medicoQueMasAtendio(Date fechaInicio, Date fechaFin) {
        // Obtener todos los médicos
        List<Medico> medicos = listarMedicos();

        // Inicializar un mapa para realizar un seguimiento de la cantidad de pacientes atendidos por cada médico
        Map<Long, Integer> pacientesAtendidosPorMedico = new HashMap<>();

        // Iterar a través de los médicos y contar la cantidad de pacientes atendidos por cada uno
        for (Medico medico : medicos) {
            int pacientesAtendidos = cantidadPacientesAtendidosPorMedico(medico.getId(), fechaInicio, fechaFin);
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
