/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.tests;

import com.example.enums.TipoRol;
import java.util.List;
import com.example.controllers.FuncionarioJpaController;
import com.example.controllers.RolesJpaController;
import com.example.models.Roles;
import com.example.models.Funcionario;
import com.example.utils.EntityFactory;

/**
 *
 * @author eduuj
 */
public class FuncionarioTest {
    public static void probarCreacionFuncionario(){
        // Obteniendo el controlador
        FuncionarioJpaController FuncionarioController =  new FuncionarioJpaController(EntityFactory.getFactory());
        RolesJpaController RolController =  new RolesJpaController(EntityFactory.getFactory());

        // Creando un funcionario
        Funcionario funcionario1= new Funcionario();
        funcionario1.setNombre("kevin");
        funcionario1.setContraseña("contraseña123");
        funcionario1.setDni(2323323);
        funcionario1.setEmail("correrro@gmail.com");
        funcionario1.setEstadoCivil("No lo se");
        funcionario1.setDomicilio("Domicilio_1");
        funcionario1.setTelefonoCelular(323232);
        funcionario1.setTelefonoFijo(323232);
        funcionario1.setFechaNacimiento("mañana");
        
        //Agregando el funcionario
        FuncionarioController.create(funcionario1);
        
        //Creando roles
        Roles rol_1 = new Roles();
        rol_1.setRol(TipoRol.MEDICO);
        
        //Agregando a funcionaro y guardando
        rol_1.setFuncionario(funcionario1);
        RolController.create(rol_1);
        
 
        //Listando los funcionarios y sus roles
        System.out.println("----------Funcionarios----------");
        List<Funcionario> FuncionarioAlmacenadas = FuncionarioController.findFuncionarioEntities();
        for (Funcionario funcionario : FuncionarioAlmacenadas) {
            System.out.println("Nombre funcionario: " + funcionario.getNombre());
            // Listando roles
            List<Roles> rolesDelFuncionario = funcionario.getRoles();
             for (Roles rol : rolesDelFuncionario){
                 System.out.println("Tiene el rol: "+ rol.getRol());
             }
            
        }
        System.out.println("----------Funcionarios----------");
    }
}
