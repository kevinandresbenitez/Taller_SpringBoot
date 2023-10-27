/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Funcionario;
import com.proyect.models.ProfesionalSalud;
import com.proyect.repositories.ProfesionalSaludRepository;

import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * el servicio ProfesionalSaludService gestiona la logica relacionada con los profesionales de la salud
 * interactúa con el repositorio ProfesionalSaludRepository para realizar operaciones de persistencia y recuperación de datos
 *
 * @author kevin
 */
@Service
@Getter
@Setter
public class ProfesionalSaludService {

    @Autowired
    private ProfesionalSaludRepository profesionalRepository;

    /**
     * obtiene una lista de todos los profesionales de la salud disponibles
     *
     * @return lista de objetos ProfesionalSalud
     */
    public List<ProfesionalSalud> listarProfesionalSalud() {
        return this.profesionalRepository.findAll();
    }

    /**
     * asigna un funcionario como profesional de la salud con un número de matricula
     *
     * @param funcionario    funcionario que se asignara como profesional de la salud
     * @param nroMatricula   numero de matricula del profesional de la salud
     */
    public void asignarFuncionarioComoProfesionalSalud(Funcionario funcionario, Long nroMatricula) {
        ProfesionalSalud profSalud = new ProfesionalSalud();
        profSalud.setFuncionario(funcionario);
        profSalud.setNroMatricula(nroMatricula);
        this.profesionalRepository.save(profSalud);
    }

    /**
     * elimina un profesional de la salud por su identificador unico
     *
     * @param id identificador unico del profesional de la salud que se va a eliminar
     */
    public void eliminarProfesionalSaludPorId(Long id) {
        this.profesionalRepository.deleteById(id);
    }

    /**
     * verifica si un funcionario es un profesional de la salud
     *
     * @param id identificador único del funcionario
     * @return `true` si el funcionario es un profesional de la salud, de lo contrario `false`
     */
    public Boolean esFuncionarioProfesionalSalud(Long id) {
        return this.profesionalRepository.existsByFuncionarioId(id);
    }

    /**
     * obtiene un profesional de la salud por su identificador unico
     *
     * @param id identificador único del profesional de la salud
     * @return objeto ProfesionalSalud si se encuentra, o `null` si no se encuentra
     */
    public Optional<ProfesionalSalud> obtenerProfSaludPorId(Long id) {
        return this.profesionalRepository.findById(id);
    }

    /**
     * obtiene un profesional de la salud por el identificador único de un funcionario asociado
     *
     * @param idFuncionario identificador único del funcionario asociado al profesional de la salud
     * @return objeto ProfesionalSalud si se encuentra, o `null` si no se encuentra
     */
    public Optional<ProfesionalSalud> obtenerProfSaludPorFuncionarioId(Long idFuncionario) {
        return this.profesionalRepository.findByFuncionarioId(idFuncionario);
    }

    /**
     * obtiene un profesional de la salud por su número de matricula
     *
     * @param nroMatricula Número de matrícula del profesional de la salud
     * @return objeto ProfesionalSalud si se encuentra, o `null` si no se encuentra
     */
    public Optional<ProfesionalSalud> findByNroMatricula(Long nroMatricula) {
        return this.profesionalRepository.findByNroMatricula(nroMatricula);
    }
}
