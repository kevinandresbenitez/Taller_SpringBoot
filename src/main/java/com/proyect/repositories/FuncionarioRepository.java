/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.repositories;
import com.proyect.models.Funcionario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kevin
 */


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query(value = "SELECT * FROM funcionario where email = :correo and contraseña = :contraseña ",nativeQuery = true)
    public Funcionario buscarFuncionarioPorCorreoYContraseña(@Param("correo") String correo,@Param("contraseña")  String contraseña);

    public Optional<Funcionario> findByDni(int dni);
}
