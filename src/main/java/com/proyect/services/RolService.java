/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.services;
import com.proyect.models.Rol;
import com.proyect.repositories.RolRepositori;
import java.util.List;
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
    private RolRepositori rolRepositori;
    public List<Rol> listarRoles(){
        return this.rolRepositori.findAll();
    }
    
    public void crear(Rol rol){
        this.rolRepositori.save(rol);
    }
    
}
