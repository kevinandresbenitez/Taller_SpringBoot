package com.proyect.services;

import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    FuncionarioRepositori funcionarioRepositori;

    public void modificarRol(Funcionario funcionario, List<Rol> roles){
        funcionario.setRoles(roles);
        funcionarioRepositori.save(funcionario);
    }
}
