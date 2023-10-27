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
    /*Retorna los pacientes que tienen triage y ese triage no estan en una consulta, y los pacientes no estan atendiendose en un box
    * @return true o false
    */
    @Query(value = "SELECT Paciente.* FROM paciente Paciente  LEFT JOIN triage on paciente.id = triage.id_paciente LEFT JOIN box on paciente.id = box.id_paciente WHERE (paciente.id IN (SELECT id_paciente FROM triage) and triage.id NOT IN (SELECT id_triage FROM consulta)) AND paciente.id NOT IN (SELECT box.id_paciente from box where id_paciente is not null)",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerAtendidosEnBox();
    
    /*Lista de pacientes para crear triages*/
    /*Retorna los pacientes que tienen ingreso y ese ingreso no estan en una consulta, y los pacientes no tienen traige o si tienen estan en una consulta
    * @return true o false
    */
    @Query(value ="SELECT Paciente.* FROM paciente LEFT JOIN ingreso on paciente.id = ingreso.id_paciente where (ingreso.id NOT IN (SELECT id_ingreso FROM Consulta )) AND (paciente.id not in(SELECT id_paciente FROM triage where triage.id not in (select id_triage from consulta))) And (paciente.id in (select id_paciente from ingreso))",nativeQuery = true)
    List<Paciente> BuscarPacientesNecesitanSerTriagados();
    
    /*EL paciente tiene ingresos previos*/
    /* Verifica si el paciente ya ingreso
    * @return true o false
    */    
    @Query(value="select paciente.* from paciente left join ingreso on ingreso.id_paciente = paciente.id where paciente.id = :id and paciente.id in (select id_paciente where ingreso.id not in (select id_ingreso from consulta))",nativeQuery = true)
    Optional<Paciente> estaElPacienteEnIngreso(@Param("id") Long id);
}
