/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyect.repositories;

import com.proyect.models.Box;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author eduardo
 */
public interface BoxRepository extends JpaRepository<Box, Long> {
  
}
