package com.proyect.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@Entity
public class Box {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private boolean ocupado = false;
    private boolean habilitado = true;
    
    @OneToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name="id_medico")
    private Medico medico;
    
}
