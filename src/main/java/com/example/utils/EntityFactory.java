package com.example.utils;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kevin
 */
public class EntityFactory {
    public static final String PERSISTANTUNIT = "com.proyecto";
    
    public static EntityManagerFactory getFactory(){
        Map<String, String> properties = new HashMap<>();
        String RutaRelativa = System.getProperty("user.dir") + "/src/main/resources/databases/Taller_uml";
        properties.put("javax.persistence.jdbc.url", ("jdbc:sqlite:"+ RutaRelativa));
        return Persistence.createEntityManagerFactory(EntityFactory.PERSISTANTUNIT,properties);            
    }
    
}
