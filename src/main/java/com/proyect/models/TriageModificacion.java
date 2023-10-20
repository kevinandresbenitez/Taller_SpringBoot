package com.proyect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "triage_modificacion")
public class TriageModificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "id_triage")
    private Triage triage;
    @Column(name = "color_viejo")
    private String colorViejo;
    @Column(name = "motivo_cambio")
    private String motivoDeCambio;


}
