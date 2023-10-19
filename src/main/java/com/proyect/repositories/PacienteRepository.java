package com.proyect.repositories;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    public Optional<Paciente> findByDni(int dni);


    List<Paciente> findByTriagesIsNull();

    List<Paciente> findByFechaNacimientoBetweenAndConsultasFechaAtencionBetween(
            Date fechaNacimientoInicio, Date fechaNacimientoFin,
            Date fechaInicio, Date fechaFin
    );

    /*Lista de pacientes para enviar a un box*/
    @Query(value = "SELECT Paciente.* FROM paciente Paciente  LEFT JOIN triage on paciente.id = triage.id_paciente WHERE triage.id NOT IN (SELECT id_triage FROM Consulta )",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerAtendidosEnBox();
    
    /*Lista de pacientes para crear triages*/
    @Query(value ="SELECT Paciente.* FROM paciente Paciente  LEFT JOIN ingreso on paciente.id = ingreso.id_paciente LEFT JOIN triage on paciente.id = triage.id_paciente WHERE ingreso.id NOT IN (SELECT id_ingreso FROM Consulta ) AND (paciente.id not in(SELECT id_paciente FROM triage) OR triage.id IN (SELECT id_triage from consulta))",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerTriagados();
}
