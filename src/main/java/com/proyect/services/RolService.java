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
 *
 * @author kevin
 */

@Service
@Getter
@Setter
public class RolService{
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    
    public List<Rol> listarRoles(){
        return this.rolRepository.findAll();
    }
    
    public void crear(Rol rol){
        this.rolRepository.save(rol);
    }
    
    public void eliminarPorId(long id){
        this.rolRepository.deleteById(id);
    }
    
    public Optional<Rol> obtenerPorId(long id){
        return this.rolRepository.findById(id);
    }

    public List<Rol> obtenerRolesDelFuncionario(long id){
        return funcionarioRepository.findById(id).get().getRoles();
    }
    
}
