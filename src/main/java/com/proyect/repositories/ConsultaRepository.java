package com.proyect.repositories;

import com.proyect.models.Consulta;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.proyect.models.Medico;
import com.proyect.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
    List<Consulta> findByFechaAtencionBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Consulta> findByMedicoAndFechaAtencionBetween(Medico medico, LocalDate fechaInicio, LocalDate fechaFin);
}
