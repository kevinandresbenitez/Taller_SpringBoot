/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.session;

import com.proyect.models.Funcionario;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
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
    
    private Funcionario funcionario;
    
    
    // Metodos de sessiones estandar
    public void startSession(Funcionario funcionario){
        this.funcionario = funcionario;
    }
    
    public void endSession(){
        this.funcionario = null;
    }
    
    public boolean existSession(){
        return this.funcionario != null;
    }
    // Metodos de sessiones estandar
    
    
}
