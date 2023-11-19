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
import java.util.Optional;
import java.util.Set;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    
    
     /**
     * Metodo para guardar una consulta médica.
     *
     * @param consulta La consulta médica que se va a guardar.
     */
    public void guardarConsulta(Consulta consulta){
        consultaRepository.save(consulta);
    }
    
    
/**
     * Obtiene una consulta médica por su ID.
     *
     * @param id Id de la consulta médica.
     * @return La consulta médica encontrada.
     * 
     */
    public Optional<Consulta> obtenerConsultaPorId(Long id){
        return consultaRepository.findById(id);
    }
    
    /**
     * Metodo para calcular la cantidad de pacientes atendidos por un médico en un rango de fechas.
     *
     * @param medico  médico para el cual se contará la cantidad de pacientes atendidos.
     * @param fechaInicio  fecha de inicio del rango.
     * @param fechaFin  fecha de fin del rango.
     * @return La cantidad de pacientes atendidos por el médico en el rango de fechas especificado.
     */
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
