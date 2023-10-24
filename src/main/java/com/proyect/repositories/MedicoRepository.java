package com.proyect.repositories;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico,Long> {
    public Boolean existsByProfesionalSaludId(Long idProfSalud);

    List<Consulta> findConsultasByIdAndConsultasFechaAtencionBetween(Long idMedico, LocalDate fechaInicio, LocalDate fechaFin);

    public Optional<Medico> findByProfesionalSaludId(Long idProfSalud);
}
