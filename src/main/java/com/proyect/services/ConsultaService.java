package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import com.proyect.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void guardarConsulta(Consulta consulta){
        consultaRepository.save(consulta);
    }

    public Consulta obtenerConsultaPorId(Long id){
        return consultaRepository.findById(id).get();
    }

    public int cantidadPacientesAtendidosPorMedico(Medico medico, LocalDate fechaInicio, LocalDate fechaFin) {

        // Obtener las consultas realizadas por el médico en el rango de fechas
        List<Consulta> consultas = consultaRepository.findByMedicoAndFechaAtencionBetween(medico, fechaInicio, fechaFin);

        // Contar la cantidad de pacientes atendidos por el médico
        Set<Long> pacientesAtendidos = new HashSet<>();
        for (Consulta consulta : consultas) {
            pacientesAtendidos.add(consulta.getPaciente().getId());
        }

        return pacientesAtendidos.size();
    }
}
