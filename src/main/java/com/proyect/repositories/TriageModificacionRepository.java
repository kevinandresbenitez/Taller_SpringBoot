package com.proyect.repositories;

import com.proyect.models.TriageModificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TriageModificacionRepository extends JpaRepository<TriageModificacion,Long> {


}
