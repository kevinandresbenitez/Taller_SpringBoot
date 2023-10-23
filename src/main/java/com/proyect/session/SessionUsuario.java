/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.session;

import com.proyect.models.Funcionario;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.services.AdministradorService;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
import com.proyect.services.ProfesionalSaludService;
import com.proyect.services.RolService;
import com.proyect.services.SectorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author kevin
 */

@Component
@Getter
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUsuario{
    @Autowired
    private FuncionarioService funcionarioService;    
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private EnfermeroService enfermeroService;
    @Autowired
    private RolService rolService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private ProfesionalSaludService profesionalSaludService;
    
    private Funcionario funcionario;
    private ProfesionalSalud profSalud;
    private Medico medic;
   
    
    
    // Metodos de sessiones estandar
    public void startSession(Funcionario funcionario){
        this.funcionario = funcionario;
        
        // Seteo las relaciones correspondientes
        if(this.isProfSalud()){
            this.profSalud = this.profesionalSaludService.obtenerProfSaludPorFuncionarioId(this.funcionario.getId()).get();
        }
        if(this.isMedic()){
            this.medic = this.medicoService.obtenerMedicoPorIdProfSalud(this.profSalud.getId()).get();
        }
        
    }
    
    public void endSession(){
        this.funcionario = null;
    }
    
    public boolean existSession(){
        return this.funcionario != null;
    }
    // Metodos de sessiones estandar
    
    // Metodos de clases
    public boolean isAdmin(){
        return this.existSession() && this.administradorService.esFuncionarioAdministrador(this.funcionario.getId());
    }
    
    public boolean isProfSalud(){
        return this.existSession() && this.profesionalSaludService.esFuncionarioProfesionalSalud(this.funcionario.getId());
    }
    public boolean isMedic(){
        return this.existSession() && this.isProfSalud() && this.medicoService.esProfSaludMedico(this.profSalud.getId());
    }    
    
    public boolean isNurse(){ 
    //Es enfermero en ingles (me arrepiendo de escribir en ingles XD) 
        return  this.existSession() && this.isProfSalud() && this.enfermeroService.esProfSaludEnfermero(this.profSalud.getId());
    }
    // Metodos de clases
    
    // Metodos de funcionario
    public boolean workIn(String sector){
        /*
        return funcionario.getSectores().contains(sector);
        */
        return true;
    }
    
    public boolean hashRol(String rol){
        /*
        return funcionario.getRoles().contains(rol);
        */
        return true;
    }
    // Metodos de funcionario
}
