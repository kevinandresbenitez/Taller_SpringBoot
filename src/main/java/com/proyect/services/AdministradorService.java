package com.proyect.services;

import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    FuncionarioRepositori funcionarioRepositori;

    public void modificarRol(Funcionario funcionario, List<Rol> rol) {
        Optional<Funcionario> funcionario_cambio_rol = funcionarioRepositori.findById(funcionario.getId());
        if(funcionario_cambio_rol.isPresent()){
            Funcionario funcionario1 = funcionario_cambio_rol.get();
            funcionario1.setRoles(rol);
            funcionarioRepositori.save(funcionario1);
        }
    }
}
