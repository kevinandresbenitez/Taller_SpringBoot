package com.proyect.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import com.proyect.models.Persona;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="paciente")
public class Paciente extends Persona implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "paciente")
    private List<contactoPaciente> contactos;
    @OneToMany(mappedBy = "paciente")
    private List<ResultadoEstudio> resultadoEstudios;

    public void agregarResultadoEstudios(ResultadoEstudio resultadoEstudio){
        this.resultadoEstudios.add(resultadoEstudio);
    }
}
