package com.proyect.repositories;

import com.proyect.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico,Long> {
    public Boolean existsByProfesionalSaludId(Long idProfSalud);
}
