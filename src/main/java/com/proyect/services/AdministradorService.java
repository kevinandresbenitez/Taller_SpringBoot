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
/**
 * gestiona los metodos basicos para realizar un CRUD de Administrador
 */
@Service
public class AdministradorService {

    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    AdministradorRepository administradorRepository;
       
    /**
 *  Lista todos los administradores registrados.
 *
 * @return Lista de  Administradores.
 */
    public List<Administrador> listarAdministradores(){
        return this.administradorRepository.findAll();
    }
    
    /**
 * Elimina un administrador por su Id.
 *
 * @param id  Id del administrador que se desea eliminar.
 */
    public void eliminarAdministradorPorId(Long id){
        this.administradorRepository.deleteById(id);
    }
   
    /**
 * Asigna a un funcionario como administrador.
 *
 * @param funcionario  funcionario que se asignará como administrador.
 */
    public void asignarFuncionarioComoAministrador(Funcionario funcionario){
        Administrador administrador = new Administrador();
        administrador.setFuncionario(funcionario);
        this.administradorRepository.save(administrador);
    
    }
    
    /**
 * Elimina el estado de administrador para un funcionario.
 *
 * @param funcionario  funcionario del cual se eliminará el estado de administrador.
 */        
    public void eliminarAdministradorParaFuncionario(Funcionario funcionario){
        Administrador administrador = this.administradorRepository.findByFuncionario(funcionario);
        this.administradorRepository.delete(administrador);
    }
    
    /**
 * Verifica si un funcionario es un administrador.
 *
 * @param id  Id del funcionario a verificar.
 * @return `true` si el funcionario es administrador, `false` en caso contrario.
 */
    public Boolean esFuncionarioAdministrador(Long id) {
        return this.administradorRepository.existsByFuncionarioId(id);
    }
    
}
