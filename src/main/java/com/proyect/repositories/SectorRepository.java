package com.proyect.repositories;

import com.proyect.models.Sector;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector,Long> {

    public List<Sector> findByFuncionariosId(Long id);
}
