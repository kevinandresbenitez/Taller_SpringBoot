package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;


import com.proyect.repositories.ConsultaRepository;
import com.proyect.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;
    /**
     * busca un paciente por su número de DNI
     *
     * @param dni numero de DNI del paciente
     * @return objeto Optional que contiene al paciente si se encuentra, o es vacio si no se encuentra
     */
    public Optional<Paciente> findByDni(int dni){
        return pacienteRepository.findByDni(dni);
    }
    /**
     * guarda un paciente en el sistema
     *
     * @param paciente objeto Paciente que se va a guardar
     */
    public void guardarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }
    /**
     * obtiene una lista de todos los pacientes registrados en el sistema
     *
     * @return lista de objetos Paciente
     */
    public List<Paciente> listarPacientes(){
        return pacienteRepository.findAll();
    }
    /**
     * obtiene un paciente por su identificador único
     *
     * @param id identificador único del paciente
     * @return objeto Optional que contiene al paciente si se encuentra, o es vacio si no se encuentra
     */
    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }
    /**
     * obtiene un paciente por su identificador unico
     * Si no se encuentra, devuelve `null`
     *
     * @param id identificador unico del paciente
     * @return objeto Paciente si se encuentra, o `null` si no se encuentra
     */
    public Paciente obtenerPacienteById(Long id){
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);

        if (pacienteOptional.isPresent()) {
            return pacienteOptional.get();
        } else {
            return null;
        }
    }
    /**
     * obtiene una lista de pacientes que necesitan ser triagados
     *
     * @return lista de objetos Paciente
     */
    public List<Paciente> obtenerPacientesNecesitanSerTriagados(){
        return pacienteRepository.BuscarPacientesNecesitanSerTriagados();
    }
    /**
     * obtiene una lista de pacientes que necesitan ser atendidos en el box
     *
     * @return lista de objetos Paciente
     */
    public List<Paciente> obtenerPacientesNecesitanSerAtendidosEnBox() {
        return pacienteRepository.BuscarPacientesNecesitanSerAtendidosEnBox();
    }
    /**
     * cuenta la cantidad de pacientes por rango de edad y fecha de atencion
     *
     * @param edadMinima   edad minima del rango
     * @param edadMaxima   edad maxima del rango
     * @param fechaInicio  fecha de inicio del rango
     * @param fechaFin     fecha de fin del rango
     * @return cantidad de pacientes que cumplen con los criterios especificados
     */
    public int cantidadPacientesPorEdadYfechaAtencion(int edadMinima, int edadMaxima, LocalDate fechaInicio, LocalDate fechaFin) {
        // Calcular las fechas de nacimiento a partir de las edades
        LocalDate fechaNacimientoMinima = LocalDate.now().minusYears(edadMaxima);
        LocalDate fechaNacimientoMaxima = LocalDate.now().minusYears(edadMinima);

        // Llama al método del repositorio personalizado
        List<Paciente> pacientes = pacienteRepository.findByFechaNacimientoBetweenAndConsultasFechaAtencionBetween(
                                                                                                                    fechaNacimientoMinima,
                                                                                                                    fechaNacimientoMaxima,
                                                                                                                    fechaInicio,
                                                                                                                    fechaFin);

        // Realiza el conteo de pacientes
        return pacientes.size();
    }

    /**
     * encuentra al paciente más consultado en un rango de fechas
     *
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin    fecha de fin del rango
     * @return objeto Paciente más consultado
     */

    public Paciente pacienteMasConsultado(LocalDate fechaInicio, LocalDate fechaFin) {

        List<Paciente> pacientesOrdenadosPorCantConsultas = pacienteRepository.findByConsultasFechaAtencionBetweenOrderByConsultasDesc(fechaInicio, fechaFin);

        return pacientesOrdenadosPorCantConsultas.get(pacientesOrdenadosPorCantConsultas.size()-1);
    }
    /**
     * verifica si un paciente está en espera
     *
     * @param id identificador único del paciente
     * @return `true` si el paciente esta en espera, `false` si no lo esta
     */
    public boolean estaElPacienteEnEspera(Long id){
        Optional<Paciente> paciente = this.pacienteRepository.estaElPacienteEnIngreso(id);
        if(paciente.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
