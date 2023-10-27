/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyect.session;

import com.proyect.models.Funcionario;
import com.proyect.models.Medico;
import com.proyect.models.ProfesionalSalud;
import com.proyect.models.Rol;
import com.proyect.models.Sector;
import com.proyect.repositories.RolRepository;
import com.proyect.services.AdministradorService;
import com.proyect.services.EnfermeroService;
import com.proyect.services.FuncionarioService;
import com.proyect.services.MedicoService;
import com.proyect.services.ProfesionalSaludService;
import com.proyect.services.RolService;
import com.proyect.services.SectorService;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Clase de session
 * 
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
    /**
     * asigna un funcionario como profesional de la salud con un número de matricula
     *
     * @param funcionario  Se guarda un funcionario, que es el de la session activa
     */
    public void startSession(Funcionario funcionario){
        this.funcionario = funcionario;
        
        // Seteo las relaciones correspondientes(Por el lazy load de Spring boot)
        if(this.isProfSalud()){
            this.profSalud = this.profesionalSaludService.obtenerProfSaludPorFuncionarioId(this.funcionario.getId()).get();
        }
        if(this.isMedic()){
            this.medic = this.medicoService.obtenerMedicoPorIdProfSalud(this.profSalud.getId()).get();
        }
        
    }
    
    /**
     * Se elimina la session del funcionario
     * 
     */
    public void endSession(){
        this.funcionario = null;
        this.profSalud = null;
        this.medic = null;
    }
    
    /**
     * Verifica si la session existe     
     * @return `true` si el funcionario inicio session, de lo contrario `false`
     */
    public boolean existSession(){
        return this.funcionario != null;
    }
    // Metodos de sessiones estandar
    
    // Metodos de clases
     /**
     * Verifica si es un funcionario administrador    
     * @return `true` si el funcionario es un administrador, en caso contrario `false`
     */
    public boolean isAdmin(){
        return this.existSession() && this.administradorService.esFuncionarioAdministrador(this.funcionario.getId());
    }
    
     /**
     * Verifica si es un funcionario profesional de la salud
     * @return `true` si el funcionario es un profesional de la salud, en caso contrario `false`
     */
    public boolean isProfSalud(){
        return this.existSession() && this.profesionalSaludService.esFuncionarioProfesionalSalud(this.funcionario.getId());
    }
    
     /**
     * Verifica si es un funcionario medico
     * @return `true` si el funcionario es medico, en caso contrario `false`
     */
    public boolean isMedic(){
        return this.existSession() && this.isProfSalud() && this.medicoService.esProfSaludMedico(this.profSalud.getId());
    }    
    
    /**
     * Verifica si es un funcionario medico con especialidades
     * @return `true` si el funcionario es medico con especialidades, en caso contrario `false`
     */
    public boolean isMedicalSpecialist(){
       return this.isMedic() && this.medicoService.tieneEspecialidades(this.medic.getId());
    }
    
    /**
     * Verifica si es un funcionario enfermero
     * @return `true` si el funcionario es enfermero, en caso contrario `false`
     */
    public boolean isNurse(){ 
        //Es enfermero en ingles (me arrepiendo de escribir en ingles XD) 
        return  this.existSession() && this.isProfSalud() && this.enfermeroService.esProfSaludEnfermero(this.profSalud.getId());
    }
    // Metodos de clases
    
    // Metodos de funcionario
    /**
     * Verifica si es un funcionario trabaja en un sector
     * * @param sectorVerificar  String
     * @return `true` si el funcionario trabaja en, en caso contrario `false`
     */
    public boolean workIn(String sectorVerificar){
        List<Sector> sectores = sectorService.obtenerSectoresDelFuncionario(this.funcionario.getId());

        for (Sector sector : sectores) {
            if (sector.getNombre().equals(sectorVerificar)) {
                return true;
            }
        }
        
        return false;
    }
    
     /**
     * Verifica si es un funcionario tiene un rol
     * * @param rolVerificar  String
     * @return `true` si el funcionario tiene un rol en, en caso contrario `false`
     */
    public boolean hashRol(String rolVerificar){
        List<Rol> roles = rolService.obtenerRolesDelFuncionario(this.funcionario.getId());

        for (Rol rol : roles) {
            if (rol.getNombre().equals(rolVerificar)) {
                return true;
            }
        }
        
        return false;
    }
    // Metodos de funcionario
}
