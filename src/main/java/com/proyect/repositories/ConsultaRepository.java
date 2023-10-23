package com.proyect.repositories;

import com.proyect.models.Consulta;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
    List<Consulta> findByFechaAtencionBetween(Date fechaInicio, Date fechaFin);
}
