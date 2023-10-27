package com.proyect.services;

import com.proyect.models.Consulta;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.ConsultaRepository;
import com.proyect.repositories.MedicoRepository;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * el servicio MedicoService gestiona la logica relacionada con los medicos
 * interactua con el repositorio MedicoRepository para realizar operaciones de persistencia
 * y recuperación de datos relacionados con los medicos
 *
 *
 */
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaService consultaService;

    /**
     * obtiene una lista de todos los medicos registrados en el sistema
     *
     * @return lista de objetos Medico
     */
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    /**
     * verifica si un profesional de la salud es medico
     *
     * @param idProfSalud identificador único del profesional de la salud
     * @return `true` si el profesional de la salud es medico, `false` si no lo es
     */
    public Boolean esProfSaludMedico(Long idProfSalud) {
        return medicoRepository.existsByProfesionalSaludId(idProfSalud);
    }

    /**
     * asigna un profesional de la salud como medico
     *
     * @param profesionalSalud Objeto ProfesionalSalud que se asignará como medico
     */
    public void asignarProfSaludComoMedico(ProfesionalSalud profesionalSalud) {
        Medico medico = new Medico();
        medico.setProfesionalSalud(profesionalSalud);
        medicoRepository.save(medico);
    }

    /**
     * elimina un medico por su identificador unico
     *
     * @param id identificador unico del médico que se eliminara
     */
    public void eliminarMedicoPorId(Long id) {
        medicoRepository.deleteById(id);
    }

    /**
     * obtiene un médico por su identificador unico
     *
     * @param id identificador único del medico
     * @return objeto Optional que contiene al médico si se encuentra, o es vacío si no se encuentra
     */
    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id);
    }

    /**
     * encuentra al medico que más pacientes atendió en un rango de fechas
     *
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin    fecha de fin del rango
     * @return objeto Medico que mas pacientes atendio
     */
    public Medico medicoQueMasAtendio(LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todos los médicos
        List<Medico> medicos = listarMedicos();

        // Inicializar un mapa para realizar un seguimiento de la cantidad de pacientes atendidos por cada médico
        Map<Long, Integer> pacientesAtendidosPorMedico = new HashMap<>();

        // Iterar a través de los médicos y contar la cantidad de pacientes atendidos por cada uno
        for (Medico medico : medicos) {
            int pacientesAtendidos = consultaService.cantidadPacientesAtendidosPorMedico(medico, fechaInicio, fechaFin);
            pacientesAtendidosPorMedico.put(medico.getId(), pacientesAtendidos);
        }

        // Encontrar al médico que atendió a más pacientes
        Long medicoIdMasAtendido = Collections.max(pacientesAtendidosPorMedico.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Obtener el médico a partir de su ID
        return obtenerMedicoPorId(medicoIdMasAtendido).orElse(null);
    }

    /**
     * obtiene un médico por el identificador único de un profesional de la salud
     *
     * @param idProfSalud identificador unico del profesional de la salud
     * @return objeto Optional que contiene al médico si se encuentra, o es vacío si no se encuentra
     */
    public Optional<Medico> obtenerMedicoPorIdProfSalud(Long idProfSalud) {
        return medicoRepository.findByProfesionalSaludId(idProfSalud);
    }

    /**
     * verifica si un medico tiene especialidades
     *
     * @param idMedico identificador único del medico
     * @return `true` si el medico tiene especialidades, `false` si no las tiene
     */
    public boolean tieneEspecialidades(Long idMedico) {
        Optional<Medico> medico = obtenerMedicoPorId(idMedico);

        if (medico.isEmpty()) {
            return false;
        }

        // si tiene titulación (relacionada directamente con especialidad), es porque tiene especialidad
        if (medico.get().getTitulos().size() > 0) {
            return true;
        }

        return false;
    }
}
