/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Funcionario;
import com.proyect.repositories.FuncionarioRepository;

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
public class FuncionarioService{
    @Autowired
    private FuncionarioRepository funcionarioRepository;

      
    public void crearFuncionario(Funcionario funcionario){
        this.funcionarioRepository.save(funcionario);
    }
    
    public List<Funcionario> listarFuncionarios(){
        return this.funcionarioRepository.findAll();
    }
    
    public Optional<Funcionario> obtenerFuncionarioPorId(Long id){
        return this.funcionarioRepository.findById(id);
    }
}
