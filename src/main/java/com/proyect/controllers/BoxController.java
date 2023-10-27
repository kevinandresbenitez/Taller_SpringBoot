/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers;

import com.proyect.models.Box;
import com.proyect.services.BoxService;
import com.proyect.session.SessionUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * esta clase maneja las operaciones relacionadas a la entidad de box
 * @author eduardo
 *
 */
@Controller
@RequestMapping("/boxes")
public class BoxController {
    @Autowired
    BoxService boxService;
    @Autowired
    SessionUsuario sessionUser;

    /**
     *
     * @param model se utiliza para tener la posibilidad de mandar variables a la vista
     * @return vista donde se ve el listado de boxes
     */
    @GetMapping("/")
      
    public String listBox(Model model) {
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Box> boxes = boxService.listarBoxes();
        model.addAttribute("boxes", boxes);
        return "boxes/index";
   
    }

    /**
     *
     * @param atributoMensaje si un box se agrego correctamente, a traves de este atributo mandamos
     *                        un mensaje a la vista avisando que fue agregado con exito
     * @return la vista con la lista de boxes
     */
    @GetMapping("/agregarbox")
    public String agregarBox(RedirectAttributes atributoMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Box> boxes = boxService.listarBoxes();
        boxService.crearBox(boxes.size()+1,false);
        atributoMensaje.addFlashAttribute("mensaje","¡Se agregó un nuevo box!");
        return "redirect:/boxes/";
    }

    /**
     *
     * @param id id necesario para poder ubicar el box en el sistema y poder eliminarlo sin problemas
     * @param atributoMensaje si un box se elimino correctamente, a traves de este atributo mandamos
     *                        un mensaje a la vista avisando que fue eliminado con exito
     * @return vista con la lista de boxes
     */
    @GetMapping("eliminar/{id}")
    public String eliminarBox(@PathVariable("id")Long id, RedirectAttributes atributoMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        boxService.eliminarBoxById(id);
        atributoMensaje.addFlashAttribute("mensaje","¡Box eliminado con éxito!");
        return "redirect:/boxes/";
    }
}
