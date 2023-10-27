package com.proyect.repositories;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    public Optional<Paciente> findByDni(int dni);


    List<Paciente> findByTriagesIsNull();

    List<Paciente> findByFechaNacimientoBetweenAndConsultasFechaAtencionBetween(
            LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFin,
            LocalDate fechaInicio, LocalDate fechaFin);

    /*Lista de pacientes para enviar a un box*/
    @Query(value = "SELECT Paciente.* FROM paciente Paciente  LEFT JOIN triage on paciente.id = triage.id_paciente LEFT JOIN box on paciente.id = box.id_paciente WHERE (paciente.id IN (SELECT id_paciente FROM triage) and triage.id NOT IN (SELECT id_triage FROM consulta)) AND paciente.id NOT IN (SELECT box.id_paciente from box where id_paciente is not null)",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerAtendidosEnBox();
    
    /*Lista de pacientes para crear triages*/
    @Query(value ="SELECT paciente.* FROM paciente LEFT JOIN ingreso on paciente.id = ingreso.id_paciente where (ingreso.id NOT IN (SELECT id_ingreso FROM Consulta )) AND (paciente.id not in(SELECT id_paciente FROM triage where triage.id not in (select id_triage from consulta)))",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerTriagados();
    
    /*EL paciente tiene ingresos previos*/
    @Query(value="select paciente.* from paciente left join ingreso on ingreso.id_paciente = paciente.id where paciente.id = 6 and paciente.id in (select id_paciente where ingreso.id not in (select id_ingreso from consulta))",nativeQuery = true)
    Optional<Paciente> estaElPacienteEnIngreso(@Param("id") Long id);
}
