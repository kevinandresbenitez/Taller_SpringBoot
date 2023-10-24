package com.proyect.repositories;

import com.proyect.models.Triage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TriageRepository extends JpaRepository<Triage,Long> {

    public List<Triage> findTriageByFechaEvaluacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
