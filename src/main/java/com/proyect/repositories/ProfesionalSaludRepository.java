/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.repositories;
import com.proyect.models.ProfesionalSalud;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kevin
 */


@Repository
public interface ProfesionalSaludRepository extends JpaRepository<ProfesionalSalud, Long> {
    public Boolean existsByFuncionarioId(Long id);

    public Optional<ProfesionalSalud> findByFuncionarioId(Long idFuncionario);
}