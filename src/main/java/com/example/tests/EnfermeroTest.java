/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.tests;
import java.util.List;
import com.example.controllers.EnfermeroJpaController;
import com.example.controllers.FuncionarioJpaController;
import com.example.utils.EntityFactory;
import com.example.models.Enfermero;
import com.example.models.Funcionario;

/**
 *
 * @author kevin
 */
public class EnfermeroTest {
    public static void probarCreacionEnfermeros(){
                // Obteniendo el controlador
        FuncionarioJpaController funcionarioController =  new FuncionarioJpaController(EntityFactory.getFactory());
        EnfermeroJpaController enfermeroJpaController =  new EnfermeroJpaController(EntityFactory.getFactory());

        // Creando un funcionario
        Funcionario funcionario1= new Funcionario();
        funcionario1.setNombre("Nombre generico de enfermero");
        funcionario1.setContraseña("contraseña123");
        funcionario1.setDni(2323323);
        funcionario1.setEmail("correrro@gmail.com");
        funcionario1.setEstadoCivil("No lo se");
        funcionario1.setDomicilio("Domicilio_1");
        funcionario1.setTelefonoCelular(323232);
        funcionario1.setTelefonoFijo(323232);
        funcionario1.setFechaNacimiento("mañana");
        
        //Agregando el funcionario
        funcionarioController.create(funcionario1);
        
        //Digo que ese funcionario sera un enfermero
        Enfermero enfermero = new Enfermero();
        enfermero.setFuncionario(funcionario1);
        
        // Creando el enfermero
        enfermeroJpaController.create(enfermero);
        
        //Listando los Enfermeros
        System.out.println("----------Enfermeros----------");
        List<Enfermero> EnfermerosAlmacenados = enfermeroJpaController.findEnfermeroEntities(0, 0);
        
        for (Enfermero enfermeroAlmacenado : EnfermerosAlmacenados) {
            System.out.println("Nombre Enfermero: " + enfermeroAlmacenado.getFuncionario().getNombre());           
        }
        System.out.println("----------Enfermeros----------");
        
        
        
    }
    
}
