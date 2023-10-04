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
 *
 * @author kevin
 */

@Service
@Getter
@Setter
public class ProfesionalSaludService{
    @Autowired
    private ProfesionalSaludRepository profesionalRepository;

    
    public List<ProfesionalSalud> listarProfesionalSalud(){
        return this.profesionalRepository.findAll();
    }

    public void asignarFuncionarioComoProfesionalSalud(Funcionario funcionario,Long nroMatricula) {
        ProfesionalSalud profSalud = new ProfesionalSalud();
        profSalud.setFuncionario(funcionario);
        profSalud.setNroMatricula(nroMatricula);
        this.profesionalRepository.save(profSalud);
    }

    public void eliminarProfesionalSaludPorId(Long id) {
        this.profesionalRepository.deleteById(id);
    }

    public Boolean esFuncionarioProfesionalSaludPorId(Long id) {
        return this.profesionalRepository.existsByFuncionarioId(id);
    }
    
}
