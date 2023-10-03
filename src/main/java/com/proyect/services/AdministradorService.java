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
    RolRepository rolRepository;
    @Autowired
    AdministradorRepository administradorRepository;

    public void modificarRol(Funcionario funcionario, List<Rol> roles){
        /* Manera No me funciona
        funcionario.setRoles(roles);
        funcionarioRepositori.save(funcionario);
        1*/
        
        /*Manera 2 Funcionar pero es menos eficaz */
        List<Rol> rolesAntiguos = rolRepository.findByFuncionarioId(funcionario.getId());
        
        //Elimino roles antiguos
        for(int i=0;rolesAntiguos.size()>i;i++){
            rolRepository.delete(rolesAntiguos.get(i));
        }
        //Agrego los nuevos
        for(int i=0;roles.size()>i;i++){
            roles.get(i).setFuncionario(funcionario);
            rolRepository.save(roles.get(i));
        }
 
    }
    
    public List<Administrador> listarAdministradores(){
        return this.administradorRepository.findAll();
    }
    
    public void eliminarAdministradorPorId(Long id){
        this.administradorRepository.deleteById(id);
    }
    
    public void crearAdministradorParaFuncionario(Funcionario funcionario){
        Administrador administrador = new Administrador();
        administrador.setFuncionario(funcionario);
        this.administradorRepository.save(administrador);
    
    }
    public Optional<Administrador> obtenerAdministradorPorFuncionarioId(Long id){
        return this.administradorRepository.findByFuncionarioId(id);
    }
            
    public void eliminarAdministradorParaFuncionario(Funcionario funcionario){
        Administrador administrador = this.administradorRepository.findByFuncionario(funcionario);
        this.administradorRepository.delete(administrador);
    }
    
}
