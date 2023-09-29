package com.proyect.services;

import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.repositories.FuncionarioRepositori;
import com.proyect.repositories.RolRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    FuncionarioRepositori funcionarioRepositori;
    @Autowired
    RolRepositori rolRepositori;

    public void modificarRol(Funcionario funcionario, List<Rol> roles){
        /* Manera No me funciona
        funcionario.setRoles(roles);
        funcionarioRepositori.save(funcionario);
        1*/
        
        /*Manera 2 Funcionar pero es menos eficaz */
        List<Rol> rolesAntiguos = rolRepositori.findByFuncionarioId(funcionario.getId());
        
        //Elimino roles antiguos
        for(int i=0;rolesAntiguos.size()>i;i++){
            rolRepositori.delete(rolesAntiguos.get(i));
        }
        //Agrego los nuevos
        for(int i=0;roles.size()>i;i++){
            roles.get(i).setFuncionario(funcionario);
            rolRepositori.save(roles.get(i));
        }
 
    }
}
