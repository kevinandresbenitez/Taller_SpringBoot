/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.controllers.Funcionario;

import com.proyect.models.Funcionario;
import com.proyect.models.Rol;
import com.proyect.models.Sector;
import com.proyect.services.FuncionarioService;
import com.proyect.services.RolService;
import com.proyect.services.SectorService;
import com.proyect.session.SessionUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * Controller para gestionar los sectores de trabajo
 */
@Controller
@Setter
@Getter
@RequestMapping("/funcionarios/sectores/")
public class SectorController {
    @Autowired
    private FuncionarioService funcionarioServices;
    @Autowired
    private SectorService sectorServices;
    @Autowired
    SessionUsuario sessionUser;
    
    
    /**
 * Controlador para listar los funcionarios.
 *
 * 
 * Se verifica si el usuario ha iniciado sesión y si tiene permisos de administrador antes de mostrar la lista de funcionarios.
 *
 * @param modelo  Model para agregar datos que se mostrarán en la vista.
 * @return Muestra la lista de funcionarios en la vista "funcionarios/index" si el usuario ha iniciado sesión.
 *  Si el usuario no ha iniciado sesión, se redirige a la página de inicio.
 */
    @GetMapping("/")
    public String list(Model modelo){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Sector> sectores = this.sectorServices.listarSectores();
        modelo.addAttribute("sectores",sectores);
        return "funcionarios/sectores/index";
    }
    
    /**
 * Controlador para asignar roles a un funcionario por su ID.
 *
 * Esta ruta permite a un administrador asignar roles a un funcionario específico identificado por su ID.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador antes de permitir la asignación de roles.
 *
 * @param id_funcionario  Id del funcionario al que se desean asignar roles, obtenido desde la URL.
 * @param modelo  Model para agregar datos que se mostrarán en la vista.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Muestra la página de asignación de roles si el usuario ha iniciado sesión y es un administrador.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 *         Si el funcionario identificado por su Id no existe, se redirige a la página de funcionarios con un mensaje de error.
 */
    @GetMapping("/asignar/{id_funcionario}")
    public String asignarRoles(@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        List<Sector> sectores = this.sectorServices.listarSectores();
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        modelo.addAttribute("funcionario",funcionario.get());
        modelo.addAttribute("sectores",sectores);
        return "funcionarios/sectores/asignar";
    }
    
    /**
 * Controlador para procesar la asignación de roles a un funcionario por su ID.
 *
 * Esta ruta permite a un administrador asignar sectores (roles) a un funcionario específico identificado por su Id.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador antes de permitir la asignación de sectores.
 *
 * @param SectoresId Una lista de Ids de sectores (roles) que se desean asignar al funcionario.
 * @param id_funcionario id funcionario al que se desean asignar sectores, obtenido desde la URL.
 * @param modelo Model para agregar datos que se mostrarán en la vista.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Redirige a la página de funcionarios con un mensaje de asignación exitosa si se asignan los sectores correctamente.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 *         Si el funcionario identificado por su Id no existe, se redirige a la página de funcionarios con un mensaje de error.
 *         Si no se selecciona al menos un sector, se redirige a la página de funcionarios con un mensaje de advertencia.
 */
    @PostMapping("/asignar/{id_funcionario}")
    public String procesarAsignacionRoles(@RequestParam("sectores_id") List<Long> SectoresId,@PathVariable("id_funcionario") Long id_funcionario,Model modelo , RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Optional<Funcionario> funcionario = this.funcionarioServices.obtenerFuncionarioPorId(id_funcionario);
                
        if(funcionario.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","No existe el funcionairo");
            return "redirect/funcionarios/";
        }
        
        if(SectoresId.isEmpty()){
            atributosMensaje.addFlashAttribute("mensaje","necesita seleccionar al menoz un sector");
            return "redirect/funcionarios/";
        }
        
        
        // Remuevo los Sectores anteriores
        funcionario.get().getSectores().removeAll(funcionario.get().getSectores());
        
        
        //Si los roles existen los agrego al funcionario
        for (Long id_sector : SectoresId) {
            Optional<Sector> sector = this.sectorServices.obtenerPorId(id_sector);
            if(sector.isPresent()){
                sector.get().getFuncionarios().add(funcionario.get());
                funcionario.get().getSectores().add(sector.get());
                //Guardo
                this.sectorServices.crear(sector.get());
                this.funcionarioServices.crearFuncionario(funcionario.get());
            }
            
        }
        
        atributosMensaje.addFlashAttribute("mensaje","se ha asignado el Sector correctamente");
        return "redirect:/funcionarios/";
    }
    
    

    /**
 * Controlador para crear un nuevo sector.
 *
 * Esta ruta permite a un administrador crear un nuevo sector mediante un formulario.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador antes de permitir la creación del sector.
 *
 * @param nombre nombre del sector a crear, obtenido desde el formulario.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Redirige a la página de sectores de funcionarios con un mensaje de creación exitosa si se crea el sector correctamente.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 */
    @PostMapping("/crear")
    public String processFormCreation(@RequestParam("nombre") String nombre,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        Sector sector = new Sector();
        sector.setNombre(nombre);
        this.sectorServices.crear(sector);
        
        //Una vez creado redirijo y envio un mensaje de creacion correcta        
        atributosMensaje.addFlashAttribute("mensaje","Se creo el sector Adecuadamente");
        return "redirect:/funcionarios/sectores/";
    }
      
    /**
 * Controlador para eliminar un sector por su ID.
 *
 * Esta ruta permite a un administrador eliminar un sector específico identificado por su ID.
 * Se verifica si el usuario ha iniciado sesión y tiene permisos de administrador antes de permitir la eliminación del sector.
 *
 * @param id Id del sector que se desea eliminar.
 * @param atributosMensaje  para agregar mensajes que se mostrarán después de la redirección.
 * @return Redirige a la página de sectores de funcionarios con un mensaje de eliminación exitosa si el sector se elimina correctamente.
 *         Si el usuario no ha iniciado sesión o no es un administrador, se redirige a la página de inicio.
 */
    @GetMapping("/eliminar/{id}")
    public String deleteFuncionary(@PathVariable("id") Long id,RedirectAttributes atributosMensaje){
        // Verificacion de session
        if(!sessionUser.existSession() || !sessionUser.isAdmin()){
            return "redirect:/";
        }
        
        this.sectorServices.eliminarPorId(id);
        atributosMensaje.addFlashAttribute("mensaje","Se ah eliminado el sector correctamente");
        return "redirect:/funcionarios/sectores/";
    }
}
