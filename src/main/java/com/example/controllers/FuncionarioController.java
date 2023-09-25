/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

/**
 *
 * @author kevin
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
    
    @GetMapping("/")
    public ModelAndView index(){
        //Cargando pagina de resources/templates/funcionario/index.html
        return new ModelAndView("funcionario/index");
    }
}
