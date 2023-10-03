package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="persona_contacto")
public class contactoPaciente extends Persona implements Serializable{
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
}
