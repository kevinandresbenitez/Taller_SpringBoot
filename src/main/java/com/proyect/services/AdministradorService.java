package com.proyect.services;

import com.proyect.models.Administrador;
import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.AdministradorRepository;
import com.proyect.repositories.FuncionarioRepository;
import com.proyect.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    AdministradorRepository administradorRepository;
        
    public List<Administrador> listarAdministradores(){
        return this.administradorRepository.findAll();
    }
    
    public void eliminarAdministradorPorId(Long id){
        this.administradorRepository.deleteById(id);
    }
    
    public void asignarFuncionarioComoAministrador(Funcionario funcionario){
        Administrador administrador = new Administrador();
        administrador.setFuncionario(funcionario);
        this.administradorRepository.save(administrador);
    
    }
            
    public void eliminarAdministradorParaFuncionario(Funcionario funcionario){
        Administrador administrador = this.administradorRepository.findByFuncionario(funcionario);
        this.administradorRepository.delete(administrador);
    }

    public Boolean esFuncionarioAdministrador(Long id) {
        return this.administradorRepository.existsByFuncionarioId(id);
    }
    
}
