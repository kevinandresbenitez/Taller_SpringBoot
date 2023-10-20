package com.proyect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Box {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero")
    private int numero;
    
    @OneToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name="id_medico")
    private Medico medico;
    
    
    public boolean estaUnMedicoAtendiendo(){
        return this.medico != null;
    }
    
    public boolean estaUnPacienteSiendoAtendido(){
        return this.paciente != null;
    }
    
}
