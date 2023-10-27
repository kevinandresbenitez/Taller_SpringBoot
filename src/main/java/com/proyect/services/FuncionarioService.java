/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepository;
import com.proyect.repositories.RolRepository;

import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar funcionarios.
 */

@Service
@Getter
@Setter


public class FuncionarioService{
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    RolRepository rolRepository;

     /**
     * Crea y guarda un funcionario.
     *
     * @param funcionario  funcionario a crear y guardar.
     */
    public void crearFuncionario(Funcionario funcionario){
        this.funcionarioRepository.save(funcionario);
    }
    
    /**
     * Lista todos los funcionarios registrados.
     *
     * @return Una lista de funcionarios.
     */
    public List<Funcionario> listarFuncionarios(){
        return this.funcionarioRepository.findAll();
    }
    
    /**
     * Obtiene un funcionario por su ID.
     *
     * @param id El Id del funcionario a buscar.
     * @return Un objeto Optional que puede contener el funcionario si se encuentra,
     * o estar vacío si no se encuentra.
     */
    public Optional<Funcionario> obtenerFuncionarioPorId(Long id){
        return this.funcionarioRepository.findById(id);
    }
    
    /**
     * Elimina un funcionario por su ID.
     *
     * @param id  Id del funcionario a eliminar.
     */
    public void eliminarFuncionarioPorId(Long id){
        this.funcionarioRepository.deleteById(id);
    }
    
    /**
     * Metodo para buscar un funcionario por su correo y contraseña.
     *
     * @param correo correo del funcionario.
     * @param contraseña contraseña del funcionario.
     * @return El funcionario encontrado o null si no se encuentra.
     */
    public Funcionario buscarFuncionarioPorCorreoYContraseña(String correo, String contraseña){
        return this.funcionarioRepository.buscarFuncionarioPorCorreoYContraseña(correo,contraseña);    
    }
    
    /**
     * Obtener un funcionario por su número de DNI.
     *
     * @param dni  DNI del funcionario a buscar.
     * @return Un objeto Optional que puede contener el funcionario si se encuentra,
     * o estar vacío si no se encuentra.
     */
    public Optional<Funcionario> obtenerFuncionarioPorDni(int dni) {
        return this.funcionarioRepository.findByDni(dni);
    }
    
}
