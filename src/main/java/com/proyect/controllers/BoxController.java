/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;

import com.proyect.models.Box;
import com.proyect.services.BoxService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author eduardo
 */
@Controller
@RequestMapping("/boxes")
public class BoxController {
    @Autowired
    BoxService boxService;
    
      @GetMapping("/")
      
         public String listBox(Model model) {
             List<Box> boxes = boxService.listarBoxes();
             model.addAttribute("boxes", boxes);
             return "boxes/index";
    
   
    }  
}
