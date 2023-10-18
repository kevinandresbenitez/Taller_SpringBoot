package com.proyect.repositories;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Date;
import java.util.List;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    public Optional<Paciente> findByDni(int dni);


    List<Paciente> findByTriagesIsNull();

    List<Paciente> findByFechaNacimientoBetweenAndConsultasFechaAtencionBetween(
            Date fechaNacimientoInicio, Date fechaNacimientoFin,
            Date fechaInicio, Date fechaFin
    );


}
