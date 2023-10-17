package com.proyect.repositories;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico,Long> {
    public Boolean existsByProfesionalSaludId(Long idProfSalud);

    List<Consulta> findConsultasByIdAndConsultasFechaAtencionBetween(long idMedico, Date fechaInicio, Date fechaFin);
}
