/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.repositories;
import com.proyect.models.Administrador;
import com.proyect.models.Funcionario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kevin
 */


@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
      Administrador findByFuncionario(Funcionario funcionario);
      Optional<Administrador> findByFuncionarioId(Long id);
      Boolean existsByFuncionarioId(Long id);
}