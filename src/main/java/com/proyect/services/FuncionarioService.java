/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepositori;
import com.proyect.repositories.RolRepositori;
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
    private final FuncionarioRepositori funcionarioRepositori;

    @Autowired
    public FuncionarioService(FuncionarioRepositori funcionarioRepositori){
        this.funcionarioRepositori = funcionarioRepositori;
    }
      
    public void crearFuncionario(Funcionario funcionario){
        this.funcionarioRepositori.save(funcionario);
    }
    
    public List<Funcionario> listarFuncionarios(){
        return this.funcionarioRepositori.findAll();
    }
}
