package com.proyect.repositories;

import com.proyect.models.ResultadoEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoEstudioRepository extends JpaRepository<ResultadoEstudio,Long> {
}
