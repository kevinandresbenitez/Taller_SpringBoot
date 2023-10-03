package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="persona")
public class Paciente extends Persona implements Serializable{
    @OneToMany(mappedBy = "paciente")
    private List<contactoPaciente> contactos;
}
