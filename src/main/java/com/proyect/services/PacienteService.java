package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Paciente;

import com.proyect.models.ResultadoEstudio;
import com.proyect.repositories.ConsultaRepository;
import com.proyect.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    public Optional<Paciente> findByDni(int dni){
        return pacienteRepository.findByDni(dni);
    }

    public void guardarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes(){
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente obtenerPacienteById(Long id){
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);

        if (pacienteOptional.isPresent()) {
            return pacienteOptional.get();
        } else {
            return null;
        }
    }

    public List<Paciente> obtenerPacientesNecesitanSerTriagados(){
        return pacienteRepository.BuscarPacientesNecesitanSerTriagados();
    }

    public List<Paciente> obtenerPacientesNecesitanSerAtendidosEnBox() {
        return pacienteRepository.BuscarPacientesNecesitanSerAtendidosEnBox();
    }
        
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



    public Paciente pacienteMasConsultado(LocalDate fechaInicio, LocalDate fechaFin) {

        // Obtener las consultas realizadas en el rango de fechas
        List<Consulta> consultas = consultaRepository.findByFechaAtencionBetween(fechaInicio, fechaFin);

        Map<Paciente, Long> conteoPacientes = consultas.stream()
                //Esto crea un mapa donde la clave es el paciente y el valor es el recuento de consultas para ese paciente.
                .collect(Collectors.groupingBy(Consulta::getPaciente, Collectors.counting()));

        Paciente pacienteMasConsultado = conteoPacientes.entrySet().stream()
                //Busca el mayor
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return pacienteMasConsultado;
    }

    public boolean estaElPacienteEnEspera(Long id){
        Optional<Paciente> paciente = this.pacienteRepository.estaElPacienteEnIngreso(id);
        if(paciente.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
